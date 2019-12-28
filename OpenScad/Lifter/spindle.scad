TotalLength = 29.425;
MaxRadius = 7.75;
MinRadius = 5;
RopeRadius = 2;
Chamfer = 0.5;
Cap = 1;
HalfSpindleLength = TotalLength/2 - Chamfer - Cap;

$fn = 72;

//translate([0,-20,0])
//Spindle(0);

//Cap(12, true);

//translate([0,-40,0])
Cap(6, false);

module Cap(CapEndThickness, addStopper) {
    CapThickness = 2;
    CapInnerWidth = 36.25;
    CapDepth = 8.25;
    CapOffset = 6.5;
    CapBlockThickness = 4;
    CapChamfer = 1;
    HookOffset = 7;
    HookLength = 14.175; // 28.75/2 to nearest print layer
    
    difference() {
        union () {
            if (addStopper) {
                p1 = Tangent([-(HookLength - HookOffset),CapDepth-2], [0,0], 4, 1);
                echo(p1);
                
                translate([CapInnerWidth/2,CapDepth-2,0])
                    cube([CapEndThickness, CapThickness+2, HookLength]);
                
                color("red")
                translate([CapInnerWidth/2,0,0])
                    rotate([0,90,0])
                        linear_extrude(height=CapEndThickness)
                            polygon([[0,CapDepth],
                                     [-(HookLength - HookOffset),CapDepth-2],
                                     [p1.x,p1.y], [0,0]]);
                
             
                translate([-(CapInnerWidth/2+CapEndThickness),CapDepth-2,0])
                    cube([CapEndThickness, CapThickness+2, HookLength]);
                
                color("red")
                translate([-(CapInnerWidth/2+CapEndThickness),0,0])
                    rotate([0,90,0])
                        linear_extrude(height=CapEndThickness)
                            polygon([[0,CapDepth],
                                     [-(HookLength - HookOffset),CapDepth-2],
                                     [p1.x,p1.y], [0,0]]);
                

                color("green")
                translate([0,CapDepth+2,0])
                    rotate([90,0,0]) {
                        linear_extrude(height=CapThickness)
                            polygon([[CapInnerWidth/2-(CapOffset+CapBlockThickness), 4],
                                     [CapInnerWidth/2,4],
                                     [CapInnerWidth/2, HookLength]]);
                        
                        linear_extrude(height=CapThickness)
                            polygon([[-(CapInnerWidth/2-(CapOffset+CapBlockThickness)), 4],
                                     [-CapInnerWidth/2,4],
                                     [-CapInnerWidth/2, HookLength]]);
                    }
            }
                         
            linear_extrude(height=8, center = true, convexity=10)
                polygon([[-CapInnerWidth/2 - CapEndThickness, CapDepth+CapThickness],
                         [-CapInnerWidth/2 - CapEndThickness, 0],
                         [-CapInnerWidth/2, 0],
                         [-CapInnerWidth/2, CapDepth],
                         [-CapInnerWidth/2, CapDepth],
                         [-CapInnerWidth/2+CapOffset-Chamfer, CapDepth],
                         [-CapInnerWidth/2+CapOffset, CapDepth-Chamfer],
                         [-CapInnerWidth/2+CapOffset, 2],
                         [-CapInnerWidth/2+CapOffset+CapBlockThickness, 2],
                         [-CapInnerWidth/2+CapOffset+CapBlockThickness,
                          CapDepth-Chamfer],
                         [-CapInnerWidth/2+CapOffset+CapBlockThickness+Chamfer,
                          CapDepth],
                         [CapInnerWidth/2-CapOffset-CapBlockThickness-Chamfer,
                          CapDepth],
                         [CapInnerWidth/2-CapOffset-CapBlockThickness,
                          CapDepth-Chamfer],
                         [CapInnerWidth/2-CapOffset-CapBlockThickness, 2],
                         [CapInnerWidth/2-CapOffset, 2],
                         [CapInnerWidth/2-CapOffset, CapDepth-Chamfer],
                         [CapInnerWidth/2-CapOffset+Chamfer, CapDepth],
                         [CapInnerWidth/2, CapDepth],
                         [CapInnerWidth/2, 0],
                         [CapInnerWidth/2+CapEndThickness, 0],
                         [CapInnerWidth/2+CapEndThickness, CapDepth+CapThickness]]);
                         
            rotate([0,90,0]) {
                translate([0,0,CapInnerWidth/2])
                    cylinder(r=4, h=CapEndThickness);
                translate([0,0,-CapInnerWidth/2-CapEndThickness])
                    cylinder(r=4, h=CapEndThickness);
                
            }
                         
        }
        union() {
            rotate([0,90,0]) {
                Spindle(0.5);
            cylinder(r=2, h =100, center=true);
            }
        }
        
    }
    
}


module Spindle(dr) {
    Mid = Tangent([HalfSpindleLength, MaxRadius+dr],
                  [0,MinRadius+dr], RopeRadius, -1);
    
    difference() {
        translate([0,0,-TotalLength/2]) {
            union () {
                translate([0,0,1])
                    cylinder(r=Mid.y, h=TotalLength-1);        
                cylinder(r1=MaxRadius-Chamfer+dr, r2=MaxRadius+dr, h=Chamfer);
                
                translate([0,0,Chamfer])
                    cylinder(r=MaxRadius+dr, h=Cap);
                
                translate([0,0,Chamfer+Cap])
                    cylinder(r1=MaxRadius+dr, r2=Mid.y,
                             h=HalfSpindleLength-Mid.x);
                
                translate([0,0,TotalLength/2+Mid.x ])
                    cylinder(r1=Mid.y, r2=MaxRadius+dr,
                             h=HalfSpindleLength-Mid.x);
                
                translate([0,0,TotalLength - Chamfer - Cap])
                    cylinder(r=MaxRadius+dr, h=Cap);
                translate([0,0,TotalLength - Chamfer])
                    cylinder(r1=MaxRadius+dr, r2=MaxRadius-Chamfer+dr,
                             h=Chamfer);
            }
        }
        union() {
            translate([0,0,-TotalLength/2 - Chamfer])
                cylinder(r1 = 2 + 2 * Chamfer, r2 = 2, h = 2 * Chamfer);
            translate([0,0,TotalLength/2 - Chamfer])
                cylinder(r1 = 2, r2 = 2 + 2 * Chamfer, h = 2 * Chamfer);
            cylinder(r=2,h=100,center=true);
            Torus(MinRadius+dr, RopeRadius);
        }   
        
    }
}

module Torus(r1, r2) {    
    rotate_extrude(angle = 360)
        translate([r1,0])
            circle(r=r2);
    
}



function Tangent(p, center, r, s) =
     let(dx = (p.x - center.x))
     let(dy = (p.y - center.y))
     let(distance2 = dx*dx + dy*dy)
     let(distance = sqrt(distance2))
     let(angleA = atan2(dy, dx))
     let(angleB = acos(r/distance))
        [center.x + cos(angleA + angleB * s) * r,
         center.y + sin(angleA + angleB * s) * r];