prog?=$(basename $(notdir $(PWD)))

$(prog): test.v [A-Z]*.v monitor
	iverilog -g2012 -o $@ -s $@ $(filter %.v,$^)`

sim: $(prog)
	vvp -n $< -fst

clean:
	rm -f $(prog)
