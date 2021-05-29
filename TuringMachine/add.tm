#Q = {init, get_num, add, final_state}
#S = {0,1,2,3,4,5,6,7,8,9,+}
#G = {0,1,2,3,4,5,6,7,8,9,+,_,=}
#q0 = init
#F = {final_state}
#B = _
#N = 2
#D init {1,2,3,4,5,6,7,8,9}_ [${0}-1]1 *r get_num
#D get_num {1,2,3,4,5,6,7,8,9}_ [${0}-1]1 *r get_num
#D get_num 0_ __ r* get_num
#D get_num +_ =_ rl add
#D add {0,1,2,3,4,5,6,7,8}1 [${0}+1]_ *l add
#D add {0,1,2,3,4,5,6,7,8,9}_ ${0}_ ** final_state

