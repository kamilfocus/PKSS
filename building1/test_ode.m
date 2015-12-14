Fcobmax=40;
init;
Yb=0;
Yk=0;
K=100; % Regulator gain
t=0;
t0=0;
Tzco=70;

for i=1:3
    global Fcob
    
Fcob=K*(20-Tr);
Fcob=min(Fcob,  Fcobmax);
Fcob=max(Fcob,0.0001);
 global Tpco
 global Tr
[Tk,Yk] = ode45(@kal,[i*600 i*600+600],Tpco );
Tpco=Yk(length(Yk)-1,1);
[Tb,Yb] =ode45(@bud,[i*600 i*600+600],Tr ); %Yb(length(Yb)) [Tb,Yb] =
Tr=Yb(length(Yb)-1,1);
figure(1)

hold on
subplot(2,1,1)
grid on
%plot(i,Tr,'o')
plot(Tb,Yb(:,1))
subplot(2,1,2)
plot(Tk,Yk(:,1))
%plot(i,Tpco,'x')
end
