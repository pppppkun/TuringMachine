#Q = {init, temp, mul, final_state}
#S = {0,1,2,3,4,5,6,7,8,9,*}
#G = {0,1,2,3,4,5,6,7,8,9,*,_,=}
#q0 = init
#F = {final_state}
#B = _
#N = 2
#D init {0,1,2,3,4,5,6,7,8,9}_ _${0} r* mul
#D mul *{0,1,2,3,4,5,6,7,8,9} =${1} r* mul
#D mul {0,1}{0,1,2,3,4,5,6,7,8,9} [${0}*${1}]_ ** final_state
#D mul 2{0,1,2,3,4} [${0}*${1}]_ ** final_state
#D mul 3{0,1,2,3} [${0}*${1}]_ ** final_state
#D mul 4{0,1,2} [${0}*${1}]_ ** final_state
#D mul 5{0,1} [${0}*${1}]_ ** final_state
