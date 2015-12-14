/*
 * main.cpp
 *
 *  Created on: 20 lis 2015
 *      Author: michal
 */

#include <iostream>
#include "CApplication.h"

#include "rapidjson/document.h"
#include "rapidjson/prettywriter.h"
#include "rapidjson/stringbuffer.h"

using namespace rapidjson;
using namespace std;

int main(int argc, char **argv)
{
    if(CApplication::GetInstance().Start("192.168.1.101", 1234))
    {
        CApplication::GetInstance().MainLoop();
    }

    return 0;
}
