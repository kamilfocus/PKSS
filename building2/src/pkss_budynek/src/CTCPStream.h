/*
 * CTCPStream.h
 *
 *  Created on: 20 lis 2015
 *      Author: michal
 */

#ifndef CTCPSTREAM_H_
#define CTCPSTREAM_H_

#include <sys/types.h>
#include <sys/socket.h>
#include <unistd.h>
#include <string>

class CTCPStream
{
private:
    int m_iSocket;
    int m_iPeerPort;
    std::string m_szPeerIP;

    CTCPStream();
    CTCPStream(int v_iSocket, struct sockaddr_in *v_psAddress);
    CTCPStream(CTCPStream const &v_oTCPStream);

public:
    ~CTCPStream();

    ssize_t Send(void const *v_pcBuffer, size_t v_iLength);
    ssize_t Receive(void *v_pcBuffer, size_t v_iLength, int v_iTimeout = 0);

    std::string GetPeerIP();
    int GetPeerPort();

    bool WaitForReadEvent(int v_iTimeout);

    enum
    {
        ConnectionClosed = 0,
        ConnectionReset = -1,
        ConnectionTimedOut = -2
    };

    friend class CTCPConnector;
};

#endif /* CTCPSTREAM_H_ */
