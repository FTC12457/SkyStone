$fn = 72;


BearingThickness = 6;
BearingRadius = 13.0/2;
RopeDiameter = 3;

CapDepth = 10;

Gap = 0.25;

    
HookOffset = 7;
Cap(6, true, true);


module Fittings() {
    color("red")
        rotate([0,90,0])
            cylinder(r = BearingRadius, h = BearingThickness, center = true);
    color("green")
        cube([32,1,1], center=true);
    color("green")
    translate([0, BearingRadius + RopeDiameter/2, 0])
    cylinder(r = RopeDiameter/2, h = 20, center = true);
}

module Cap(CapEndThickness, addStop, addBottomStop) {

    HookLength = addBottomStop ? 14
                               : 14.175; // 28.75/2 to nearest print layer


    BearingOverlap = -0.5;
    InnerEdge = BearingThickness/2 + BearingOverlap;
    InnerTrackEdge = 32/2 - Gap;
    OuterTrackEdge = 36.5/2;
    OuterEdge = OuterTrackEdge + CapEndThickness;
    
    CapTopThickness = 3.5;
    HookThickness = CapTopThickness + 2;
    
    BottomEdge = 8 + Gap;
    TopEdge = BottomEdge + CapTopThickness;
    
    CapBottomEdge = addStop ? -16 : 0;
    
    HookBottomEdge = BottomEdge + CapTopThickness - HookThickness;


difference() {
    union () {
        translate([-InnerTrackEdge, 0, -CapDepth/2])
            cube([InnerTrackEdge*2, TopEdge, CapDepth]);
        
        translate([OuterTrackEdge, CapBottomEdge, -CapDepth/2])
            cube([CapEndThickness, CapTopThickness + 8 - CapBottomEdge, CapDepth]);
        translate([-(OuterTrackEdge+CapEndThickness), CapBottomEdge, -CapDepth/2])
            cube([CapEndThickness, CapTopThickness + 8 - CapBottomEdge, CapDepth]);
        
        translate([-InnerTrackEdge, 0, 0])
            rotate([0,90,0])
                cylinder(r = CapDepth/2, h = 2*InnerTrackEdge);
        translate([InnerEdge, 0, 0])
            rotate([0,90,0])
                cylinder(r = CapDepth/2, h = InnerTrackEdge - InnerEdge);
        
        translate([-(OuterTrackEdge+CapEndThickness), CapBottomEdge, 0])
            rotate([0,90,0])
                cylinder(r = CapDepth/2, h = CapEndThickness);
        translate([OuterTrackEdge, CapBottomEdge, 0])
            rotate([0,90,0])
                cylinder(r = CapDepth/2, h = CapEndThickness);
        
        translate([-OuterEdge, BottomEdge, -CapDepth/2])
            cube([OuterEdge*2, TopEdge - BottomEdge, CapDepth]);
            
        if (addStop){    
            translate([OuterTrackEdge, HookBottomEdge, 0])
                cube([CapEndThickness, HookThickness, HookLength]);
                
            translate([-OuterEdge, HookBottomEdge, 0])
                cube([CapEndThickness, HookThickness, HookLength]);
                
            p1 = Tangent([-(HookLength - HookOffset), HookBottomEdge], [0,0], CapDepth/2, 1);
            color("red")
            translate([OuterTrackEdge,0,0])
                rotate([0,90,0])
                    linear_extrude(height=CapEndThickness)
                        polygon([[0,BottomEdge],
                                 [-(HookLength - HookOffset),HookBottomEdge],
                                 [p1.x,p1.y], [0,0]]);
                                 
            translate([-OuterEdge,0,0])
                rotate([0,90,0])
                    linear_extrude(height=CapEndThickness)
                        polygon([[0,BottomEdge],
                                 [-(HookLength - HookOffset),HookBottomEdge],
                                 [p1.x,p1.y], [0,0]]);
                                 
            color("green")
            translate([0,BottomEdge,0])
                rotate([-90,0,0]) {
                    linear_extrude(height=CapTopThickness)
                        polygon([[0, 0],
                                 [OuterTrackEdge, -HookLength],
                                 [OuterTrackEdge, 0]]);
                    }
            color("green")
            translate([0,BottomEdge,0])
                rotate([-90,0,0]) {
                    linear_extrude(height=CapTopThickness)
                        polygon([[0, 0],
                                 [-OuterTrackEdge, -HookLength],
                                 [-OuterTrackEdge, 0]]);
                    }
        }
        
        if (addBottomStop) {
            translate([OuterTrackEdge, HookBottomEdge, -HookLength])
                cube([CapEndThickness, HookThickness, HookLength]);
                
            translate([-OuterEdge, HookBottomEdge, -HookLength])
                cube([CapEndThickness, HookThickness, HookLength]);
                
            p1 = Tangent([(HookLength - HookOffset), HookBottomEdge], [0,0], CapDepth/2, -1);
            color("red")
            translate([OuterTrackEdge,0,0])
                rotate([0,90,0])
                    linear_extrude(height=CapEndThickness)
                        polygon([[0,BottomEdge],
                                 [(HookLength - HookOffset),HookBottomEdge],
                                 [p1.x,p1.y], [0,0]]);
                                 
            translate([-OuterEdge,0,0])
                rotate([0,90,0])
                    linear_extrude(height=CapEndThickness)
                        polygon([[0,BottomEdge],
                                 [(HookLength - HookOffset),HookBottomEdge],
                                 [p1.x,p1.y], [0,0]]);
                                 
            color("green")
            translate([0,BottomEdge,0])
                rotate([-90,0,0]) {
                    linear_extrude(height=CapTopThickness)
                        polygon([[0, 0],
                                 [OuterTrackEdge, HookLength],
                                 [OuterTrackEdge, 0]]);
                    }
            color("green")
            translate([0,BottomEdge,0])
                rotate([-90,0,0]) {
                    linear_extrude(height=CapTopThickness)
                        polygon([[0, 0],
                                 [-OuterTrackEdge, HookLength],
                                 [-OuterTrackEdge, 0]]);
                    }
        }
    }
    
    union () {
        translate([0,BearingRadius + Gap, 0])
            scale([BearingThickness/2 + Gap, RopeDiameter, 1])
                cylinder(r = 1, h = 100, center = true);

        translate([-(BearingThickness/2 + Gap), -100 + BearingRadius + Gap, -50])
            cube([BearingThickness + 2 * Gap, 100, 100]);
        
        rotate([0,90,0])
            cylinder(r = 2, h = 100, center = true);
 
        translate([-(OuterTrackEdge+2),-16,0])
            rotate([0,-90,0])
                cylinder(r = 3, h = 200);
        translate([OuterTrackEdge+2,-16,0])
            rotate([0,90,0])
                cylinder(r = 3, h = 200);

        translate([0,-16,0])
            rotate([0,90,0])
                cylinder(r = 2, h = 100, center = true);
    }
}


/*
    difference() {
    
        CapBlockThickness = 4;
    CapChamfer = 1;
    HookOffset = 7;
    HookLength = 14.175; // 28.75/2 to nearest print layer

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
  */  
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