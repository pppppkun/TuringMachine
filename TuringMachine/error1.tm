#Q = {init,write,final_state}
#S = {0,1,2,3,4,5,6,7,8,9,a,b,c,d,e,f,g,h,i,j,k,l,n,m}
#G = {0,1,2,3,4,5,6,7,8,9,a,b,c,d,e,f,g,h,i,j,k,l,n,m,_}
#q0 = init
#F = {final_state}
#B = _
#N = 2
#D init !{_}_ _${0} rr write
#D write !{_}_ _${0} *l write
#D write _!{_} ${1}_ lr write
#D write __ __ ** final_state