/*
 * CApplication.h
 *
 *  Created on: 23 lis 2015
 *      Author: michal
 */

#ifndef CAPPLICATION_H_
#define CAPPLICATION_H_

#include "CBuilding.h"
#include "CRadiator.h"
#include "CClient.h"
#include "CController.h"
#include "rapidjson/document.h"

class CApplication
{
private:
    static CApplication *m_pApp;

    CClient m_oClient;
    CBuilding m_oBuilding;
    CRadiator m_oRadiator;
    CController m_oController;
    double m_dSeconds;
    int m_iStep;

    double m_dInsideTemp;
    double m_dWaterOutTemp;

    CApplication();
    CApplication(CApplication const &);
    CApplication &operator=(CApplication const &);
    ~CApplication();

    bool CheckJSON(rapidjson::Document &v_oJSON);

public:
    static CApplication &GetInstance();
    bool Start(std::string v_szAddress, int v_iPort);
    void MainLoop();
};

#endif /* CAPPLICATION_H_ */
