#Q = {init, A, B,z}
#F = {f,z}
#S = {a,b,_,c,d}
#G = {c,d,e,f,g}
#q0 = init
#N = 1
#B = _
#D init a _ r A
#D init _ _ * f
#D init b _ r B
#D A a a r A
#D A b a r B
#D A _ a * f
#D B b b r B
#D B _ b * f
#D B a b r A
#D A c d r A