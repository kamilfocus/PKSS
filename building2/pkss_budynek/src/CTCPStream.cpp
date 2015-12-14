/*
 * CTCPStream.cpp
 *
 *  Created on: 20 lis 2015
 *      Author: michal
 */

#include <arpa/inet.h>
#include "CTCPStream.h"

CTCPStream::CTCPStream(int v_iSocket, struct sockaddr_in *v_psAddress) :
        m_iSocket(v_iSocket)
{
    char cIP[50];
    inet_ntop(PF_INET, (struct in_addr*)&(v_psAddress->sin_addr.s_addr), cIP, sizeof(cIP) - 1);
    m_szPeerIP = cIP;
    m_iPeerPort = ntohs(v_psAddress->sin_port);

    return;
}

CTCPStream::~CTCPStream()
{
    close(m_iSocket);
}

ssize_t CTCPStream::Send(void const *v_pcBuffer, size_t v_iLength)
{
    return write(m_iSocket, v_pcBuffer, v_iLength);
}

ssize_t CTCPStream::Receive(void *v_pcBuffer, size_t v_iLength, int v_iTimeout)
{
    if(v_iTimeout <= 0)
    {
        return read(m_iSocket, v_pcBuffer, v_iLength);
    }

    if(WaitForReadEvent(v_iTimeout) == true)
    {
        return read(m_iSocket, v_pcBuffer, v_iLength);
    }

    return ConnectionTimedOut;
}

std::string CTCPStream::GetPeerIP()
{
    return m_szPeerIP;
}

int CTCPStream::GetPeerPort()
{
    return m_iPeerPort;
}

bool CTCPStream::WaitForReadEvent(int v_iTimeout)
{
    fd_set sSocketSet;
    struct timeval sTimeVal;

    sTimeVal.tv_sec = v_iTimeout;
    sTimeVal.tv_usec = 0;

    FD_ZERO(&sSocketSet);
    FD_SET(m_iSocket, &sSocketSet);

    if(select(m_iSocket + 1, &sSocketSet, NULL, NULL, &sTimeVal) > 0)
    {
        return true;
    }
    else
    {
        return false;
    }
}
