(use-modules (srfi srfi-64)      
             (superduper)) ;; import function

(test-begin "supertest")

;;Test1
(test-equal "super-duper with 123 count 1"
    123   ;; expected output
    (super-duper 123 1))

;;Test2
(test-equal "super-duper with 123 count 2"
    123   ;; expected output
    (super-duper 123 2))

;;Test3
(test-equal "super-duper with '() count 1"
    '()   ;; expected output
    (super-duper '() 1))

;;Test4
(test-equal "super-duper with '() count 2"
    '()   ;; expected output
    (super-duper '() 2))

;;Test5
(test-equal "super-duper with '(x) count 1"
    '(x)   ;; expected output
    (super-duper '(x) 1))

;;Test6
(test-equal "super-duper with '(x) count 2"
    '(x x)   ;; expected output
    (super-duper '(x) 2))

;;Test7
(test-equal "super-duper with '(x y) count 1"
    '(x y)   ;; expected output
    (super-duper '(x y) 1))
;;Test8
(test-equal "super-duper with '(x y) count 2"
    '(x x y y)   ;; expected output
    (super-duper '(x y) 2))

;;Test9
(test-equal "super-duper with '((a b) y) count 3"
    '((a a a b b b) (a a a b b b) (a a a b b b) y y y)   ;; expected output
    (super-duper '((a b) y) 3))




(test-end "supertest")




