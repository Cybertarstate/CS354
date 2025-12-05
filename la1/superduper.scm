(define-module (superduper))

(define-public (super-duper source count)
    (define (new-super source count2)
        (if (= count2 0)
            (super-duper (cdr source) count)
	    (cons (super-duper (car source) count) (new-super source (- count2 1)))))
            
(if (pair? source)
    (cons (super-duper (car source) count) (new-super source (- count 1)))
    source))
    


(display(super-duper '(1 2 3) 3))
