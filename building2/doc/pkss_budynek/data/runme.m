Tzco = 70;
To = 0;
mh = 3000;
ch = 2700;
kh = 12000;
Fcob = 40;
dzeta = 1000;
cw = 4200;
mb = 20000;
cb = 1000;
kext = 15000;

Tpco0 = 0;
Tr0 = 0;

Kp = 1;%0.5;
Ki = 0;%0.00025;
Kd = 0;%25;

SP = 100;

step = 1;

sim('budynek.slx', 7200)

dT = diff(Tinside)/step;
dTm = max(dT);
dTmi = find(dT == dTm);

Tmd = Tinside(dTmi);

t = [0:1:5 * dTmi];
g = @(t) (dTm * t - dTm * dTmi + Tmd);

plot(tout, Tinside)
grid on
hold on
xlabel('Time [s]')
ylabel('T_{inside} [^oC]')
plot(t, g(t), 'r')
plot(tout, max(Tinside), '-.k')
legend('Step response', 'Tangent')
%%
L = max(find(g(t) <= 0));
R = max(Tinside) / dTm;

Kp = 0.35 * R / (L);
Ki = Kp / (2.4 * L);
Kd = Kp * 0.5 * L;

SP = 20;

sim('budynek.slx', 15000)
plot(tout, Tinside)
grid on
hold on

Kp = 0.5;
Ki = 0.0005;
Kd = 50;
sim('budynek.slx', 15000)
plot(tout, Tinside, 'r')
xlabel('Time [s]')
ylabel('T_{inside} [^oC]')
legend('Z-N method', 'Autotune')