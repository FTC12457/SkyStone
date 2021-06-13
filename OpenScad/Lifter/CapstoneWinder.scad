Height = 25.425 - 10;

$fn = 72;



difference() {
    cylinder(r = 12, h=Height);
    union() {
        rotate([0,0,45])
            translate([0,-12,Height/2])
                rotate([0,90,0])
                    Torus(5,1.25);
        rotate([0,0,45])
            translate([0,12,Height/2])
                rotate([0,90,0])
                    Torus(5,1.25);
        translate([0,0,-1])
            cylinder(r=4,h=Height+2);
        translate([8,0,-1])
            cylinder(r=2,h=Height+2);
        translate([-8,0,-1])
            cylinder(r=2,h=Height+2);
        translate([0,8,-1])
            cylinder(r=2,h=Height+2);
        translate([0,-8,-1])
            cylinder(r=2,h=Height+2);
    }
}

module Torus(r1, r2) {    
    rotate_extrude(angle = 360)
        translate([r1,0])
            circle(r=r2);
    
}