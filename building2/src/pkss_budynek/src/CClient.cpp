/*
 * CClient.cpp
 *
 *  Created on: 20 lis 2015
 *      Author: michal
 */

#include "CClient.h"

CClient::CClient() : m_szAddress(""), m_iPort(0), m_poConnector(NULL), m_poStream(NULL)
{
}

CClient::CClient(std::string v_szAddress, int v_iPort) :
        m_szAddress(v_szAddress), m_iPort(v_iPort), m_poConnector(NULL), m_poStream(NULL)
{
}

CClient::~CClient()
{
    if(m_poConnector != NULL)
    {
        delete m_poConnector;
    }
    if(m_poStream != NULL)
    {
        delete m_poStream;
    }
}

void CClient::SetParameters(std::string v_szAddress, int v_iPort)
{
    m_szAddress = v_szAddress;
    m_iPort = v_iPort;

    return;
}

bool CClient::Connect()
{
    if(m_poConnector != NULL)
    {
        delete m_poConnector;
    }
    if(m_poStream != NULL)
    {
        delete m_poStream;
    }
    m_poConnector = new CTCPConnector();
    m_poStream = m_poConnector->Connect(m_szAddress.c_str(), m_iPort);
    if(m_poStream)
    {
        return true;
    }
    else
    {
        return false;
    }
}

ssize_t CClient::Send(void const *v_pcBuffer, size_t v_iLength)
{
    return m_poStream->Send(v_pcBuffer, v_iLength);
}

ssize_t CClient::Receive(void *v_pcBuffer, size_t v_iLength, int v_iTimeout)
{
    return m_poStream->Receive(v_pcBuffer, v_iLength, v_iTimeout);
}
