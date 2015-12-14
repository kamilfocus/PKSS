/*
 * CApplication.cpp
 *
 *  Created on: 23 lis 2015
 *      Author: michal
 */

#include "CApplication.h"
#include "CRungeKutta.h"
#include "rapidjson/prettywriter.h"
#include "rapidjson/stringbuffer.h"

#include <iostream>
#include <string>

#define CALC_STEP                   1

#define PENETRATION_RATE            12000

#define RADIATOR_TOTAL_WEIGHT       3000
#define RADIATOR_SPECIFIC_HEAT      2700
#define RADIATOR_MAX_WATER_FLOW     40

#define BUILDING_AIR_WEIGHT         20000
#define BUILDING_SPECIFIC_HEAT      1000
#define BUILDING_HEAT_LOSS_RATE     15000

CBuildingConsts oBuildingConsts(BUILDING_AIR_WEIGHT, BUILDING_SPECIFIC_HEAT,
        BUILDING_HEAT_LOSS_RATE, PENETRATION_RATE);

CRadiatorConsts oRadiatorConsts(RADIATOR_TOTAL_WEIGHT, RADIATOR_SPECIFIC_HEAT,
        PENETRATION_RATE, RADIATOR_MAX_WATER_FLOW);

CApplication *CApplication::m_pApp = NULL;

CApplication::CApplication() :
        m_oBuilding(CBuilding(oBuildingConsts)),
        m_oRadiator(CRadiator(oRadiatorConsts)),
        m_dSeconds(0),
        m_iStep(0),
        m_dInsideTemp(0),
        m_dWaterOutTemp(0)
{
    m_oController.SetParameters(0.5, 0, 0);
}

CApplication::~CApplication()
{
    if(m_pApp != NULL)
    {
        delete m_pApp;
    }
}

bool CApplication::CheckJSON(rapidjson::Document &v_oJSON)
{
    rapidjson::Document::MemberIterator oIterator;

    oIterator = v_oJSON.FindMember("trzy_miliony");
    if(oIterator == v_oJSON.MemberEnd())
    {
        return false;
    }

    oIterator = v_oJSON.FindMember("T_o");
    if(oIterator == v_oJSON.MemberEnd())
    {
        return false;
    }

    oIterator = v_oJSON.FindMember("T_zco");
    if(oIterator == v_oJSON.MemberEnd())
    {
        return false;
    }

    oIterator = v_oJSON.FindMember("kp_b2");
    if(oIterator == v_oJSON.MemberEnd())
    {
        return false;
    }

    oIterator = v_oJSON.FindMember("ki_b2");
    if(oIterator == v_oJSON.MemberEnd())
    {
        return false;
    }

    oIterator = v_oJSON.FindMember("kd_b2");
    if(oIterator == v_oJSON.MemberEnd())
    {
        return false;
    }

    oIterator = v_oJSON.FindMember("T_b2");
    if(oIterator == v_oJSON.MemberEnd())
    {
        return false;
    }

    return true;
}

CApplication &CApplication::GetInstance()
{
    if(m_pApp == NULL)
    {
        m_pApp = new CApplication();
    }

    return *m_pApp;
}

bool CApplication::Start(std::string v_szAddress, int v_iPort)
{
    m_oClient.SetParameters(v_szAddress, v_iPort);

    if(m_oClient.Connect())
    {
        rapidjson::Document oHandshakeJSON;
        oHandshakeJSON.SetObject();
        oHandshakeJSON.AddMember("type", "init", oHandshakeJSON.GetAllocator());
        oHandshakeJSON.AddMember("src", "budynek2", oHandshakeJSON.GetAllocator());

        rapidjson::StringBuffer oBuffer;
        rapidjson::Writer<rapidjson::StringBuffer> oWriter(oBuffer);
        oHandshakeJSON.Accept(oWriter);

        m_oClient.Send(oBuffer.GetString(), oBuffer.GetSize());
        return true;
    }
    else
    {
        return false;
    }
}

void CApplication::MainLoop()
{
    while(true)
    {
        char aBuffer[4096] = {0x00};

        m_oClient.Receive(aBuffer, sizeof(aBuffer), 0);

        std::cout << aBuffer << std::endl;

        rapidjson::Document oDataJSON;
        oDataJSON.Parse(aBuffer);

        if(CheckJSON(oDataJSON))
        {
            double dWaterFlow = 0;

            m_iStep = 60 * oDataJSON["trzy_miliony"].GetInt();
            m_oController.SetSP(oDataJSON["T_b2"].GetDouble());
            m_oController.SetParameters(
                    oDataJSON["kp_b2"].GetDouble(),
                    oDataJSON["ki_b2"].GetDouble(),
                    oDataJSON["kd_b2"].GetDouble());
            for(int iCnt = 0; iCnt < m_iStep / CALC_STEP; iCnt++)
            {
                double dCV = m_oController.Control(m_dInsideTemp, CALC_STEP);
                m_oRadiator.SetFlow(dCV * CALC_STEP);
                m_oRadiator.UpdateParameters(oDataJSON["T_zco"].GetDouble(), m_dInsideTemp);
                m_dWaterOutTemp = CRungeKutta::Solve(m_oRadiator, CALC_STEP, m_dSeconds, m_dWaterOutTemp);
                dWaterFlow += (((double)RADIATOR_MAX_WATER_FLOW) / 3600) * m_oRadiator.GetFlow() * CALC_STEP;

                m_oBuilding.UpdateParameters(m_dWaterOutTemp, oDataJSON["T_o"].GetDouble());
                m_dInsideTemp = CRungeKutta::Solve(m_oBuilding, CALC_STEP, m_dSeconds, m_dInsideTemp);
            }
            m_dSeconds += m_iStep;
            std::cout << m_dInsideTemp << " " << m_dWaterOutTemp << std::endl;

            rapidjson::Document oResponseJSON;
            rapidjson::Value oT_pcob;
            rapidjson::Value oF_cob;
            rapidjson::Value oU_b;
            rapidjson::Value oT_r;

            oT_pcob.SetDouble(m_dWaterOutTemp);
            oF_cob.SetDouble(dWaterFlow * (((double)3600) / ((double)m_iStep)));
            oU_b.SetDouble(m_oRadiator.GetFlow());
            oT_r.SetDouble(m_dInsideTemp);

            oResponseJSON.SetObject();
            oResponseJSON.AddMember("type", "data", oResponseJSON.GetAllocator());
            oResponseJSON.AddMember("src", "budynek2", oResponseJSON.GetAllocator());
            oResponseJSON.AddMember("T_pcob2", oT_pcob, oResponseJSON.GetAllocator());
            oResponseJSON.AddMember("F_cob2", oF_cob, oResponseJSON.GetAllocator());
            oResponseJSON.AddMember("U_b2", oU_b, oResponseJSON.GetAllocator());
            oResponseJSON.AddMember("T_r2", oT_r, oResponseJSON.GetAllocator());

            rapidjson::StringBuffer oBuffer;
            rapidjson::Writer<rapidjson::StringBuffer> oWriter(oBuffer);
            oResponseJSON.Accept(oWriter);

            char const *pbuf = oBuffer.GetString();

            std::string oString(pbuf);

            std::cout << oString << std::endl << std::endl;
            std::cout << "T_pcob = " << m_dWaterOutTemp << std::endl;
            std::cout << "F_cob = " << dWaterFlow * (((double)3600) / ((double)m_iStep)) << std::endl;
            std::cout << "U_b = " << m_oRadiator.GetFlow() << std::endl;
            std::cout << "T_r = " << m_dInsideTemp << std::endl << std::endl;

            m_oClient.Send(oBuffer.GetString(), oBuffer.GetSize());
        }
        else
        {
            rapidjson::Document oResponseJSON;
            oResponseJSON.SetObject();
            oResponseJSON.AddMember("type", "data", oResponseJSON.GetAllocator());
            oResponseJSON.AddMember("src", "budynek2", oResponseJSON.GetAllocator());

            rapidjson::StringBuffer oBuffer;
            rapidjson::Writer<rapidjson::StringBuffer> oWriter(oBuffer);
            oResponseJSON.Accept(oWriter);

            char const *pbuf = oBuffer.GetString();

            std::string oString(pbuf);

            std::cout << oString << std::endl;
            m_oClient.Send(oBuffer.GetString(), oBuffer.GetSize());
        }
    }
}
