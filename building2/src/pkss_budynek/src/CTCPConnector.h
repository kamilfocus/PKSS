/*
 * CTCPConnector.h
 *
 *  Created on: 20 lis 2015
 *      Author: michal
 */

#ifndef CTCPCONNECTOR_H_
#define CTCPCONNECTOR_H_

#include <netinet/in.h>
#include "CTCPStream.h"

class CTCPConnector
{
private:
    int ResolveHostName(char const *v_pcHost, struct in_addr *v_psAddress);

public:
    CTCPStream *Connect(char const *v_pcServer, int v_iPort);
    CTCPStream *Connect(char const *v_pcServer, int v_iPort, int v_iTimeout);
};

#endif /* CTCPCONNECTOR_H_ */
