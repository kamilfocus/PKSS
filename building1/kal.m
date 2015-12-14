function [kal_out]=kal(t,Tpco1)


global Fcob
global qw
global cw
global Tzco
global kh
global Tr
global mh
global ch

kal_out=(((Fcob/3600)*qw*cw)*(Tzco-Tpco1)-kh*(Tpco1-Tr))/(mh*ch);

%kal_out=((40*1000*4200)*(Tzco-10)-12000*(10-15))/(3000*2700)

end