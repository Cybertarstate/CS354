module Fibonacci#(BITS='d32)(
    input CLK,
    input      [BITS-1:0]INP, input      IE,
    output reg [BITS-1:0]OUT,OUT2, output reg OE,
    output [BITS-1:0] str1_dbg,
    output [BITS-1:0] str2_dbg,
    output [BITS-1:0] add_dbg);

   wire [BITS-1:0]ctr_out, add_out,str1_out,str2_out;
   wire mux_oe, ctr_oe, ctr_init, add_oe,str1_oe,str2_oe,str_init,mux2_oe;

   localparam false=1'b0;
   localparam true=1'b1;
   localparam zero=BITS'(0);
   localparam one=BITS'(1);

   Multiplexer#(.BITS(BITS))
       mux(.CLK(CLK),
	   .A(add_out), .B(zero),
	   .Y(OUT),
	   .IE(ctr_oe), .OE(mux_oe),
	   .SEL(ctr_init));	// 0->A, 1->B
    

   Counter#(.BITS(BITS))
       ctr(.CLK(CLK),
	   .INP(INP),     .IE(IE),
	   .OUT(ctr_out), .OE(ctr_oe),
           .DECR(true),		// count down
           .STOP(one),		// for (OUT=INP; OUT>1; OUT--)
	   .CE(add_oe),
	   .INIT(ctr_init), .DONE(OE));

   Adder#(.BITS(BITS))
       add(.CLK(CLK),
	   .A(str1_out),      .B(str2_out),
	   .IEA(str1_oe), .IEB(str2_oe),
	   .Y(add_out),  .OE(add_oe));

    // INSTANCE 1: F_old (Stores F(n-2))
    // It loads the output of F_new (F_new_out) to implement the shift.
    Store#(.BITS(BITS), .FIRST(1)) 
        old_store (
            .CLK(CLK),
            .INP (str1_out),      // Input is the previous F(n-1) from F_new's output
            .OUT (str2_out),      // Output is F(n-2)
            .IE  (mux_oe),       // Controlled by global enable pulse
            .OE  (str2_oe),
            .INIT(ctr_init)        // Used to initialize F(0) = 0
        );

    // ----------------------------------------------------------------
    // INSTANCE 2: F_new (Stores F(n-1))
    // It loads the new sum (F(n)) from the Adder/Mux (store_in_new).
    Store#(.BITS(BITS), .FIRST(0)) // Use FIRST=1 to seed F(1) = 1
        new_store (
            .CLK(CLK),
            .INP (OUT),   // Input is from mux
            .OUT (str1_out),      // Output is F(n-1)
            .IE  (mux_oe),
            .OE  (str1_oe),
            .INIT(ctr_init)        
        );


    // Debug output connections
    assign str1_dbg = str1_out;
    assign str2_dbg = str2_out;
    assign add_dbg  = add_out;

endmodule
