/*
 * CClient.h
 *
 *  Created on: 20 lis 2015
 *      Author: michal
 */

#ifndef CCLIENT_H_
#define CCLIENT_H_

#include <string>
#include "CTCPConnector.h"

class CClient
{
private:
    std::string m_szAddress;
    int m_iPort;
    CTCPConnector *m_poConnector;
    CTCPStream *m_poStream;

public:
    CClient();
    CClient(std::string v_szAddress, int v_iPort);
    ~CClient();

    void SetParameters(std::string v_szAddress, int v_iPort);
    bool Connect();
    ssize_t Send(void const *v_pcBuffer, size_t v_iLength);
    ssize_t Receive(void *v_pcBuffer, size_t v_iLength, int v_iTimeout = 0);
};

#endif /* SRC_CCLIENT_H_ */
