$fn = 72;

motorInnerRadius = 37.5/2;
motorSeparation = 80;
mountWidth = 96;

mountHeight = 32;
overhang = 28;


wallThickness = 4;
floorThickness = 3;

mountDepth = 32 + wallThickness;
motorOuterRadius = motorInnerRadius+wallThickness;



Mount();

bandWidth=13;
bandThickness = 1.0;
cornerRadius = 2.5;

            

module Mount() {
                   w = mountWidth + (floorThickness );

    difference() {
        union () {
            translate([-motorSeparation/2, 0,0])
                cube([motorSeparation, motorInnerRadius+wallThickness, mountDepth]);
            translate([-16,0,0])
                cube([32,32,mountDepth]);
            translate([-mountWidth/2, -floorThickness,0])
                cube([mountWidth, wallThickness+floorThickness, mountDepth]);

            translate([16,motorInnerRadius+wallThickness, 0])
                scale([40 - motorInnerRadius - 16, 32 - (motorInnerRadius+wallThickness), 1])
                    cylinder(r = 1, h = mountDepth);
            translate([-16,motorInnerRadius+wallThickness, 0])
                scale([40 - motorInnerRadius - 16, 32 - (motorInnerRadius+wallThickness), 1])
                    cylinder(r = 1, h = mountDepth);
            
            translate([mountWidth/2,(wallThickness - floorThickness)/2,0])
                cylinder(r = (wallThickness + floorThickness)/ 2, h = mountDepth);
            translate([-mountWidth/2,(wallThickness - floorThickness)/2,0])
                cylinder(r = (wallThickness + floorThickness)/ 2, h = mountDepth);
            translate([-w/2, -(overhang+floorThickness), 0]) 
                cube([w, overhang, wallThickness]);

                 
            translate([-w/2, -(floorThickness+cornerRadius),wallThickness])
                cube([w,cornerRadius+floorThickness,cornerRadius]);
            
        }
        union () {
            translate([motorSeparation/2, motorInnerRadius+wallThickness, 0]) {
                cylinder(r = motorInnerRadius, h = 100, center=true);
                translate([0,0,mountDepth/2])
                Band();
            }
            translate([-motorSeparation/2, motorInnerRadius+wallThickness, 0]) {
                cylinder(r = motorInnerRadius, h = 100, center=true);
                translate([0,0,mountDepth/2])
                Band();
            }
                            
            translate([0,0,wallThickness + 8])
                rotate([90,0,0])
                    cylinder(r=2, h = 100, center = true);
            translate([0,-0,wallThickness + 24])
                rotate([90,0,0])
                    cylinder(r=2, h = 100, center = true);
            translate([8,0,wallThickness + 16])
                rotate([90,0,0])
                    cylinder(r=2, h = 100, center = true);
            translate([-8,0,wallThickness + 16])
                rotate([90,0,0])
                    cylinder(r=2, h = 100, center = true);
            
            translate([32,-(floorThickness + 16),0])
                    Holes();
            translate([-32,-(floorThickness + 16),0])
                    Holes();
            
            translate([0,-(floorThickness+cornerRadius),wallThickness + cornerRadius])
            rotate([0,90,0])
                cylinder(r = cornerRadius,
                         h = 200, center = true);
                         


            translate([(w-overhang)/2,-floorThickness,-1])
                rotate([0,0,-90])
                    CornerCut(overhang*2,overhang,100);
            scale([-1,1,1])
                translate([(w-overhang)/2,-floorThickness,-1])
                    rotate([0,0,-90])
                        CornerCut(overhang*2,overhang,100);

        }
    }
}

module CornerCut(width, height, thickness) {
    difference() {
        cube([1+width/2,1+height/2,thickness]);
    
        scale([1,height/width,1])
            translate([0,0,-1])
                cylinder(r=width/2,h=thickness+2);
    }
}

module Holes(top=true,left=true,right=true,bottom=true) {
    if (left) {
        translate([8,0,0])
            cylinder(r=2,h=100,
                     center=true); }
    if (right) {
        translate([-8,0,0])
            cylinder(r=2,h=100,
                     center=true); }
    if (bottom) {            
        translate([0,8,0])
            cylinder(r=2,h=100,
                     center=true); }
    if (top) {
        translate([0,-8,0])
            cylinder(r=2,h=100,
                     center=true); }  
}


module Band()  {
            rotate_extrude(convexity=10)
            translate([motorOuterRadius+bandThickness/2+0.005,0,0]) {
                translate([0,bandWidth/2])
                    circle(r=bandThickness/2);
                translate([0,-bandWidth/2])
                    circle(r=bandThickness/2);
                polygon([//[0,bandWidth/2+1],
                         [bandThickness/2,bandWidth/2],
                         [bandThickness/2,-bandWidth/2],
                        // [0,-bandWidth/2-1],
                         [-bandThickness/2,-bandWidth/2],
                         [-bandThickness/2,bandWidth/2]]);
            }
}