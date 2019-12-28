Height = 25.425 - 10;

$fn = 72;



difference() {
    cylinder(r = 11, h=Height);
    union() {
        rotate([0,0,45])
            translate([0,11,Height/2])
                rotate([0,90,0])
                    Torus(4,2);
        
        TappedHole(3.25,Height);
        translate([0,0,Height-3])
            cylinder(r=4.25,h=Height+2);
        translate([8,0,0])
            TappedHole(2,Height);
        translate([-8,0,0])
            TappedHole(2,Height);
        translate([0,8,0])
            TappedHole(2,Height);
        translate([0,-8,0])
            TappedHole(2,Height);
    }
}

module Torus(r1, r2) {    
    rotate_extrude(angle = 360)
        translate([r1,0])
            circle(r=r2);
    
}

module TappedHole(r, h) {
    translate([0,0,-1])
        cylinder(r=r, h = h + 2);
    translate([0,0,-0.5])
        cylinder(r1 = r + 1, r2 = r, h = 1);
}