/*
 * CTCPConnector.cpp
 *
 *  Created on: 20 lis 2015
 *      Author: michal
 */

#include <stdio.h>
#include <string.h>
#include <netdb.h>
#include <arpa/inet.h>
#include <fcntl.h>
#include <errno.h>
#include "CTCPConnector.h"

int CTCPConnector::ResolveHostName(char const *v_pcHost, struct in_addr *v_psAddress)
{
    struct addrinfo *psResolved;

    int iResult = getaddrinfo(v_pcHost, NULL, NULL, &psResolved);
    if(iResult == 0)
    {
        memcpy(v_psAddress, &((struct sockaddr_in*)psResolved->ai_addr)->sin_addr,
                sizeof(struct in_addr));
        freeaddrinfo(psResolved);
    }

    return iResult;
}

CTCPStream *CTCPConnector::Connect(char const *v_pcServer, int v_iPort)
{
    struct sockaddr_in sAddress;
    memset(&sAddress, 0, sizeof(sAddress));

    sAddress.sin_family = AF_INET;
    sAddress.sin_port = htons(v_iPort);
    if(ResolveHostName(v_pcServer, &sAddress.sin_addr) != 0)
    {
        inet_pton(PF_INET, v_pcServer, &sAddress.sin_addr);
    }

    int iSocket = socket(AF_INET, SOCK_STREAM, 0);
    if(::connect(iSocket, (struct sockaddr*)&sAddress, sizeof(sAddress)) != 0)
    {
        perror("connect() failed");

        return NULL;
    }

    return new CTCPStream(iSocket, &sAddress);
}

CTCPStream *CTCPConnector::Connect(char const *v_pcServer, int v_iPort, int v_iTimeout)
{
    if(v_iTimeout == 0)
    {
        return Connect(v_pcServer, v_iPort);
    }

    struct sockaddr_in sAddress;
    memset(&sAddress, 0, sizeof(sAddress));

    sAddress.sin_family = AF_INET;
    sAddress.sin_port = htons(v_iPort);
    if(ResolveHostName(v_pcServer, &sAddress.sin_addr) != 0)
    {
        inet_pton(PF_INET, v_pcServer, &sAddress.sin_addr);
    }

    long lArg;
    fd_set sSocketSet;
    struct timeval sTimeVal;
    socklen_t sLength;
    int iResult = -1, iValopt, iSocket = socket(AF_INET, SOCK_STREAM, 0);

    lArg = fcntl(iSocket, F_GETFL, NULL);
    lArg |= O_NONBLOCK;
    fcntl(iSocket, F_SETFL, lArg);

    if((iResult = ::connect(iSocket, (struct sockaddr*)&sAddress, sizeof(sAddress))) < 0)
    {
        if(errno == EINPROGRESS)
        {
            sTimeVal.tv_sec = v_iTimeout;
            sTimeVal.tv_usec = 0;
            FD_ZERO(&sSocketSet);
            FD_SET(iSocket, &sSocketSet);
            if(select(iSocket + 1, NULL, &sSocketSet, NULL, &sTimeVal) > 0)
            {
                sLength = sizeof(int);
                getsockopt(iSocket, SOL_SOCKET, SO_ERROR, (void*)&iValopt, &sLength);
                if(iValopt)
                {
                    fprintf(stderr, "connect()error %d - %s\n", iValopt, strerror(iValopt));
                }
                else
                {
                    iResult = 0;
                }
            }
            else
            {
                fprintf(stderr, "connect() timed out\n");
            }
        }
        else
        {
            fprintf(stderr, "connect() error %d - %s\n", errno, strerror(errno));
        }
    }

    lArg = fcntl(iSocket, F_GETFL, NULL);
    lArg &= ~O_NONBLOCK;
    fcntl(iSocket, F_SETFL, lArg);

    if(iResult == -1)
    {
        return NULL;
    }
    else
    {
        return new CTCPStream(iSocket, &sAddress);
    }
}
