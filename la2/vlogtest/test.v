// File: test.v
`timescale 1ns / 1ps

module test;
  // Signals to connect to the design
  reg  a_tb;
  reg  b_tb;
  wire y_tb;

  // Instantiate (create) the design you are testing
  and_gate uut (
    .a(a_tb),
    .b(b_tb),
    .y(y_tb)
  );

  // This block runs once at the start
  initial begin
    $display("Time | a | b | y"); // Print a header
    $monitor("%0tns | %b | %b | %b", $time, a_tb, b_tb, y_tb); // Monitor signals

    // Apply test inputs
    a_tb = 0; b_tb = 0;
    #10; // Wait 10 ns
    a_tb = 0; b_tb = 1;
    #10;
    a_tb = 1; b_tb = 0;
    #10;
    a_tb = 1; b_tb = 1;
    #10;
    $finish; // End the simulation
  end
endmodule