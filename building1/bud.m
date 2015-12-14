function [bud_out]=bud(t,Tr1)

global To
global kh
global kext
global mb
global cb
global Tpco
A=kh*(Tpco-Tr1);
B=kext*(Tr1-To);
C=mb*cb;
bud_out=((A-B))/C;
%bud_out=(kh*(Tpco-Tr1)-kext*(Tr1-To))/(mb*cb);
%bud_out=kh*(Tpco-Tr1)+((-1)*kext)*(Tr1-To);
%kal_out=((40*1000*4200)*(Tzco-10)-12000*(10-15))/(3000*2700)

end