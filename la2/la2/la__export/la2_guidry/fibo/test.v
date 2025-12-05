`timescale 1ns/100ps

module fibo#(BITS=8)();

   reg clk;
   reg [BITS]inp; // data read: ARD -> EPT
   reg [BITS]out; // data writ: EPT -> ARD
   reg ie, oe;    // input/output enable

   wire [BITS-1:0] str1_dbg_tb; 
   wire [BITS-1:0] str2_dbg_tb;
   wire [BITS-1:0] add_dbg_tb;   

   Fibonacci#(.BITS(BITS))
       f(.CLK(clk),
         .INP(inp), .IE(ie),
         .OUT(out), .OE(oe),
         .str1_dbg(str1_dbg_tb),
         .str2_dbg(str2_dbg_tb),
         .add_dbg(add_dbg_tb));

   always #15 clk=~clk;	// create a 66Mhz clock (15ns period)

   integer i;
   initial begin
      clk=1'b0;			// enabled
      // 0 1 2 3  4   5   6
      // 1 1 2 6 24 120 208
      //$monitor("TIME: %0t | INP: %1d | OUT: %1d | str1_dbg: %d | str2_dbg: %d | add_dbg: %d", 
        //     $time, inp, out, str1_dbg_tb, str2_dbg_tb, add_dbg_tb);
      for (i=0; i<=6; i++) begin
	 ie=1;
	 inp=i;			// provide input
	 #30;
	 ie=0;			// input latched during ie
	 wait (oe);		// until output good
	 $display("fibo(%1d)=%1d",inp,out);
      end
     $stop;
   end

// `include "monitor"

endmodule
