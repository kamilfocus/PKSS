clear all
suma =0;
e1=0;
kp=0.05;
ki=0.0001;
kd=0.0001;
deltaT=0;
init =struct('type', 'init', 'src', 'regulator_1');%Ramka init
json_init=savejson('',init);

%% Connect
t = tcpip('192.168.1.101', 1234, 'NetworkRole', 'client');
set(t, 'Timeout', 200, 'InputBufferSize', 30000);
fopen(t)
fwrite(t, json_init)
while(t.BytesAvailable==0)
end 
    received=fread(t, t.BytesAvailable, 'char');
    rec = JSON.parse(char(received'));
everything=0;
while(everything==0)
    everything=1;

    send =struct('type', 'data', 'src', 'regulator_1');%RESZTA_DANYCH); Ramka wysylania
    json_send=savejson('',send);
    fwrite(t, json_send);
%     pause(1.0)
    while(t.BytesAvailable==0)
    end
    received=fread(t, t.BytesAvailable, 'char');
    rec = JSON.parse(char(received'));
    if( isfield(rec,'T_o'))
        To=rec.T_o;
    else everything=0;
        '1'
    end
    if( isfield(rec,'T_zm'))
        Tzco=rec.T_zm;
    else everything=0;
        '2'
    end
    if( isfield(rec,'T_pcob1'))
        Tzco1=rec.T_pcob1;
    else everything=0;
        '3'
        Tzco1=0;
    end
    if( isfield(rec,'T_pcob2'))
        Tzco2=rec.T_pcob2;
    else everything=0;
        '4'
    end
    if( isfield(rec,'F_cob1'))
        Fcob1=rec.F_cob1;
    else everything=0;
        '5'
        Fcob1=0;
    end
    if( isfield(rec,'F_cob2'))
        Fcob2=rec.F_cob2;
    else everything=0;
        '6'
    end
    
    if( isfield(rec,'kp_reg1'))
        kp=rec.kp_reg1;
    else everything=0;
        '7'
    end

    if( isfield(rec,'ki_reg1'))
        ki=rec.kp_reg1;
    else everything=0;
        '8'
    end

    if( isfield(rec, 'kd_reg1'))
        kd=rec.kd_reg1;
    else everything=0;
        '9'
    end

    deltaT=60*rec.trzy_miliony;
    'asd'
end
    
while(1)    

    Tpco=(Tzco1*Fcob1+Tzco2*Fcob2)/(Fcob1+Fcob2)   % TO DO WYS£ANIA
    Fzco=(Fcob1+Fcob2);                               % TO DO WYS£ANIA

    SP=55-1.75*To;
    PV=Tzco;

    e=PV-SP
    roznica=e1-e
    CV_PID=kp*e+ki*suma*deltaT+kd*roznica/deltaT
    suma=suma+e;
    e1=e;
    CV_saturated= min(1, max(0,CV_PID))
    CV=80*CV_saturated;
    Fzm=CV;                                      % TO DO WYS£ANIA

    send =struct('type', 'data', 'src', 'regulator_1', 'T_pco', Tpco, 'F_zco', Fzco, 'F_zm', Fzm, 'U_m', CV_saturated);%RESZTA_DANYCH); Ramka wysylania
    json_send=savejson('',send);
    fwrite(t, json_send);
%     pause(1.0)
    while(t.BytesAvailable==0)
    end
    received=fread(t, t.BytesAvailable, 'char');
    rec = JSON.parse(char(received'));
    
    To=rec.T_o;
    Tzco=rec.T_zm;
    Tzco1=rec.T_pcob1;
%     Tzco1=0;
    Tzco2=rec.T_pcob2;
    Fcob1=rec.F_cob1;
%     Fcob1=0;
    Fcob2=rec.F_cob2;
    kp=rec.kp_reg1;
    ki=rec.ki_reg1;
    kd=rec.kd_reg1;
%     prevT=T;
    deltaT=60*rec.trzy_miliony;
%     deltaT=60*(T-prevT);
    
end
% fclose(t)

%% wysylanie
% data = JSON.parse(d)
% data.type