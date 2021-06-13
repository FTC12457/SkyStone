$fn = 72;
Gap = 0.25;

EncoderRadius = 14;
BearingRadius = 0.375 * 25.4 * 0.5;
BearingLength = 0.125 * 25.4;


ShaftSideThickness = 3.0;//BearingLength;
ScrewSeparation = 19.05;
ScrewRadius = 2.44 / 2;
BaseHeight = 15.75 * 2; //?
BaseWidth = 38.86;

Thickness = 50;  //?
ScrewLength = 10;

InnerThickness = 26;//14 + 3.13 + 1.24 + 0.035 * 2 * 25.4 + 2.45 + 2.88;
MountThickness = InnerThickness +
                 2 * ShaftSideThickness;

ChannelThickness = 31.925 - 6 - 2 * Gap;


WallThickness = 3;

p0 = [0,0];                     r0 = EncoderRadius + 1;
r1 = 8;
p1 = [r0-r1, 25];

p01a = RRTangent(p0, p1, r0, r1, -1);
p10a = RRTangent(p1, p0, r1, r0, 1);


r2i = 5; r2o = 20;

dxy2 = [p01a.x - p10a.x, p01a.y - p10a.y];
l = sqrt(dxy2.x * dxy2.x + dxy2.y * dxy2.y);
nxy2 = [-dxy2.y, dxy2.x];

p2 = [p10a.x + dxy2.x * 9 / l + nxy2.x * r2i/l,
      p10a.y + dxy2.y * 9 / l + nxy2.y * r2i/l];

r3 = 2 + WallThickness;
Angle = 15;
p3 = [p1.x + (r1 + 12) * cos(Angle), p1.y + (r1 + 12) * sin(Angle)];


p01b = RRTangent(p0, p1, r0, r1, 1);
p10b = RRTangent(p1, p0, r1, r0, -1);

p02i = RLTangent(p0, p2, r0, r2i, -1);
p20i = RLTangent(p2, p0, r2i, r0, -1);

p23i = RLTangent(p2, p3, r2i, r3, 1);
p32i = RLTangent(p3, p2, r3, r2i, 1);

p31 = RRTangent(p3, p1, r3, r1, -1);
p13 = RRTangent(p1, p3, r1, r3, 1);

rotate([0,90,0])
    rotate([0, 0, atan2(p01b.x - p10b.x, p01b.y - p10b.y)]) {
        Mount();
        //translate([p3.x, p3.y, 0])
        //rotate([0,0,atan2(p01a.x - p10a.x, p01a.y - p10a.y)+55])
        //color("green") cube([14,100,(MountThickness+ChannelThickness)/2], center=true);
    }

module Mount() {
    difference () {
        union () { 
            CylinderAt(p3, r3, ChannelThickness);
            //CylinderAt(p3, 7, MountThickness);
            
            color("red") {
                CylinderAt(p0, r0, MountThickness);
                CylinderAt(p1, r1, MountThickness);            
                linear_extrude(height = MountThickness, center=true)
                    polygon([p01a, p10a, p10b, p01b]);
            }
            
            linear_extrude(height = ChannelThickness, center = true)
                polygon([p23i, p32i, p31, p13, p0, p02i, p20i]);
        }
        
        union() {
            translate([-50, -100, -InnerThickness/2])
                cube([100, 100, InnerThickness]);
            
            
            cylinder(r = BearingRadius+Gap/4, h = 100,
                    center=true);
            
            CylinderAt(p2, r2i, ChannelThickness+2);
            CylinderAt(p3, 2, ChannelThickness+2);
            translate([ScrewSeparation/2, 0, 0])
                cylinder(r = ScrewRadius+Gap/2, h = 100,
                        center=true);
            translate([-ScrewSeparation/2, 0, 0])
                cylinder(r = ScrewRadius+Gap/2, h = 100,
                        center=true);
            Wheel(21, 2, InnerThickness);
            translate([p13.x, p13.y,0])
            Torus(4, 2);
            
            
            rotate([0,0,atan2(p1.y - p0.y, p1.x - p0.x)])
            translate([13,0,0]) {
                cube([10,50,InnerThickness - 4], center = true);
                translate([-2,0,0])
                    cube([10,50,InnerThickness], center = true);
                translate([3,0,-(InnerThickness/2 - 2)])
                    rotate([90,0,0])
                        cylinder(r = 2, h = 55, center=true);
                translate([3,0,(InnerThickness/2 - 2)])
                    rotate([90,0,0])
                        cylinder(r = 2, h = 55, center=true);
            }
        }
    }
}

module CylinderAt(p, r, h)  {
    translate([p.x, p.y, 0])
        cylinder(r = r, h = h, center=true);
}

module MarkAll(pp) {
    for (i = [0:len(pp)-1])
        Mark(pp[i], i+1);
}
module Mark(p, h) {
    echo(p);
    translate([p.x, p.y, 0])
        cylinder(r = 1, h = h);
}

module Insert() {
    ShaftRadius = 0.25 * 25.4 / 2;
    ScrewRadius = 1.92 / 2;
    ScrewOffset = (10.2 - 2*ScrewRadius) * sqrt(2)/2;
    OuterRadius = ScrewOffset + ScrewRadius + 0.5 + Gap;
    SetScrewRadius = 2.84/2;
    Thickness = 6.005;
    ShaftCut = 0.225 * 25.4 - ShaftRadius;
    
    difference() {
            cylinder(r = OuterRadius, h = Thickness);
        
        translate([0,0,-1])
        union () {
            rotate([0,0,45])
                union() {
                    translate([0,0,Thickness/2 + 1])
                    rotate([90,0,0])
                    cylinder(r = SetScrewRadius, h = 10);
                    intersection () {
                        translate([-5,-(ShaftCut+Gap),0])
                            cube([10,10,Thickness+2]);
                        cylinder(r = ShaftRadius + Gap,
                                 h = Thickness + 2);
                    }
                }
            translate([ScrewOffset, 0, 0])
                cylinder(r = ScrewRadius + Gap, h = Thickness + 2);
            translate([-ScrewOffset, 0, 0])
                cylinder(r = ScrewRadius + Gap, h = Thickness + 2);
            translate([0, ScrewOffset, 0])
                cylinder(r = ScrewRadius + Gap, h = Thickness + 2);
            translate([0, -ScrewOffset, 0])
                cylinder(r = ScrewRadius + Gap, h = Thickness + 2);
        }
    }
}

module Wheel(outerRadius, rollerRadius, width) {
    CoreOffset = width/2 - rollerRadius;
    
    translate([0,0,CoreOffset])
        Torus(outerRadius - rollerRadius, rollerRadius);
    translate([0,0,-CoreOffset])
        Torus(outerRadius - rollerRadius, rollerRadius);
    
    cylinder(r = outerRadius - rollerRadius,
             h = width, center=true);
    cylinder(r = outerRadius,
             h = 2 * CoreOffset, center=true);
}

module Torus(r1, r2) {    
    rotate_extrude(angle = 360)
        translate([r1,0])
            circle(r=r2);
    
}

function RRTangent(p1, p2, r1, r2, s) =
     let(dx = (p2[0] - p1[0]))
     let(dy = (p2[1] - p1[1]))
     let(distance2 = dx*dx + dy*dy)
     let(distance = sqrt(distance2))
     let(nx = dx / distance)
     let(ny = dy / distance)
     let(cosA = (r1-r2)/distance)
     let(sinA = sqrt(1-cosA*cosA))
        [p1[0] + (cosA * r1) * nx - s * (sinA * r1) * ny,
         p1[1] + (cosA * r1) * ny + s * (sinA * r1) * nx];


function RLTangent(p1, p2, r1, r2, s) =
     let(dx = (p2[0] - p1[0]))
     let(dy = (p2[1] - p1[1]))
     let(distance2 = dx*dx + dy*dy)
     let(distance = sqrt(distance2))
     let(angleA = atan2(dy, dx))
     let(angleB = acos((r1+r2)/distance))
        [p1[0] + cos(angleA + angleB * s) * r1,
         p1[1] + sin(angleA + angleB * s) * r1];

