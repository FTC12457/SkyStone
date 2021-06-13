
$fn = 72;

Tilt = 20;

WallHeight = 25;
BatteryDepth = 76;
BatteryWidth = 11;
BatteryLength = 147;

PhoneCornerRadius = 4;

CutOffset = 4.4;
FloorThickness = 10;
SideThickness = 2 * cos(Tilt);
FloorInset = 3;

ClipThickness = 2;
ClipRadius = 40;
ClipLength = 25;
ClipRotate = 31;
ClipShift = 6;

EndThickness = ClipShift+ClipThickness/2;

HoleInset = 2.5;

t = 12;
s = (BatteryWidth/2 + SideThickness);
x = s * cos(Tilt);
y = s * sin(Tilt);
o = x + (y + (CutOffset-2)) * tan(Tilt);


HoleX = 4 + BatteryWidth/2 - (t+o);
Hole1Y = BatteryLength/2-64;
Hole2Y = BatteryLength/2-32;
Hole3Y = BatteryLength/2;
Hole4Y = BatteryLength/2+32;
Hole5Y = BatteryLength/2+64;

//rotate([0,0,45])
difference() {
    difference() { 
        union () {
            translate([-(t+o),t,-CutOffset]) {
                color("blue") cube([t, BatteryLength-t*2, 2]);
            translate([t,0,0])
                cylinder(r = t, h = 2);
            translate([t,BatteryLength-t*2,0])
                cylinder(r = t, h = 2);
            }
            
            h = 4 - 4 * sin(Tilt);
            l = 4 * cos(Tilt) - h * tan(Tilt);
            translate([-(o+l),0,6-CutOffset]) {
                difference() {
                    translate([0,0,-4])
                    cube([4,BatteryLength,h]);
                    rotate([-90,0,0])
                        translate([0,0,-1])
                            cylinder(r = 4, h = BatteryLength+2);
                }
            }
            
            rotate([0,Tilt,0])
            translate([-BatteryWidth/2,0,0])
            union() {
                //color("red")
                    //cube([BatteryWidth,BatteryLength,BatteryDepth]);


                translate([1,-EndThickness,-FloorThickness])
                    cube([BatteryWidth-2, BatteryLength+2*EndThickness,
                          FloorThickness]);
                
                translate([-SideThickness,0,-FloorThickness])
                    cube([BatteryWidth+30, BatteryLength,
                          FloorThickness]);
                
                difference() {
                    union() {
                        translate([-SideThickness,0,-1])
                            cube([SideThickness, BatteryLength, WallHeight+1]);
                        rotate([-90,90,0])
                            Corner(PhoneCornerRadius,BatteryLength);
                    }
                    
                    translate([0,0,WallHeight])
                        rotate([180,-90,0])
                            translate([0,0,-(SideThickness+1)])
                                Corner(WallHeight,SideThickness+2);
                    
                    translate([0,BatteryLength,WallHeight])
                        rotate([0,-90,0])
                            translate([0,0,-1])
                                Corner(WallHeight,SideThickness+2);
                }
                
                difference() {
                    union() {
                        translate([BatteryWidth,0,-1])
                            cube([30, BatteryLength, WallHeight+1]);
                        translate([BatteryWidth,0,0])
                            rotate([-90,0,0])
                            Corner(PhoneCornerRadius,BatteryLength);
                    }
                    
                    translate([BatteryWidth,0,WallHeight])
                        rotate([180,-90,0])
                            translate([0,0,-1])
                                Corner(WallHeight,30);
                    
                    translate([BatteryWidth,BatteryLength,WallHeight])
                        rotate([0,-90,0])
                            translate([0,0,-(SideThickness+40)])
                                Corner(WallHeight,42+SideThickness);
                }
                    
                
                translate([1,0,-FloorThickness])
                    Corner(SideThickness+1, FloorThickness);
                
                translate([BatteryWidth-1,0,-FloorThickness])
                    rotate([0,0,90])
                        Corner(SideThickness+1, FloorThickness);

                translate([1,BatteryLength,-FloorThickness])
                    rotate([0,0,-90])
                        Corner(SideThickness+1, FloorThickness);
                
                translate([BatteryWidth-1,BatteryLength,-FloorThickness])
                    rotate([0,0,180])
                    Corner(SideThickness+1, FloorThickness);        
             
                translate([BatteryWidth/2,-ClipShift,0])
                    Clip();
                
                translate([BatteryWidth/2,BatteryLength+ClipShift,0])
                    rotate([0,0,180])
                        Clip();
            }
        }
        translate([-BatteryWidth/2,0,0])
        union () {
            translate([-250,-250,-(500+CutOffset)])
            cube([500,500,500.0]);
            
            translate([HoleX,Hole2Y,-HoleInset]) {
                translate([0,0,-FloorThickness-5])
                    cylinder(r=2,h=FloorThickness+11);
            }
            
            translate([HoleX,Hole3Y,-HoleInset]) {
                translate([0,0,-FloorThickness-5])
                    cylinder(r=2,h=FloorThickness+11);
            }
            
            translate([HoleX,Hole4Y,-HoleInset]) {
                translate([0,0,-FloorThickness-5])
                    cylinder(r=2,h=FloorThickness+11);
            }
       } 
    }

    translate([WallHeight * sin(Tilt) + (BatteryWidth/2+SideThickness) * cos(Tilt), -200, -200])
    cube([200, 400, 400]);
}
module Clip() {
    union() {
        rotate([0,90,0]) {
            translate([0,4,-(BatteryWidth-2)/2])
                rotate([0,0,-90])
                    Corner(3,BatteryWidth-2);
            rotate([0,0,90-ClipRotate])
                translate([0,0,-(BatteryWidth - 2)/2]) {
                    Arc(ClipRadius,ClipThickness, ClipLength,BatteryWidth-2);

                }
                translate([0,0,-(BatteryWidth - 2)/2])
                color("red")
                    linear_extrude(height = BatteryWidth-2, convexity=4)
                        polygon([[1,0],[1,4],[-7,4],[-3,1],[0,0]]);
            }
    }
}

module Corner(radius, length) {
    translate([-radius,-radius,0])
        difference() {
                cube([radius+1, radius+1, length]);
            translate([0,0,-1])
            cylinder(r=radius,h=length+2);
        }  
}

module Arc(outerRadius, thickness, length, height)
{
    radius = outerRadius-thickness/2;
    innerRadius = outerRadius - thickness;
    translate([-radius,0,0]) {
        angle = length * 180 / (radius * 3.141592654);
        x = cos(angle) * radius;
        y = sin(angle) * radius;

        union() {
            translate([x,y,0])
                cylinder(r=thickness/2, h=height);
            translate([radius,0,0])
                cylinder(r=thickness/2, h=height);
            intersection() {
                HollowCylinder(innerRadius, outerRadius, height);

                translate([0,0,-1])
                    linear_extrude(height = height + 2)
                        polygon([[0,0],[2*radius,0],
                                 [2*radius,2*radius],
                                 if (angle > 90)
                                    [-2*radius,2*radius],
                                 if (angle > 180)
                                    [-2*radius,-2*radius],
                                 if (angle > 270)
                                    [2*radius,-2*radius],
                                 [2*x,2*y]]);
            }
        }
    }
}

module HollowCylinder(innerRadius, outerRadius, height)
{
    difference()
    {
        cylinder(r=outerRadius, h=height, $fn = 288);
        translate([0,0,-1])
            cylinder(r=innerRadius, h=height + 2, $fn = 288);
    }
}

module RoundedTriangle(points, rInset, rCorner, height)
{
    insetPoints = [for (i=[0:len(points)-1])
                   Inset3(points[(i + len(points) - 1) % len(points)],
                          points[i], 
                          points[(i + 1) % len(points)], rInset)];

    linear_extrude(height=height)
        offset(r=rCorner)
            polygon(insetPoints, convexity=10);
}


// Line equation:
//  P12(t) = (p2 + n12) + t * d12
//  P34(t) = (p3 + n34) + t * d34
//
// Solve for t12, t34 such that P12(t12) = P34(t34)
//
// P12(t12) - P34(t34) = (p2+n12)  - (p3+n34) + t12 * d12 - t34 * d34 = 0
//                       t12 * d12 - t34 * d34 =  (p3+n34) - (p2+n12)
//
//  d12[0] * t12 - d23[0] * t23 = n23[0] * o23 + n12[0] * o12;
function Inset3(p1, p2, p3, offset) = Inset4(p1, p2, p2, p3, offset);
function Inset4(p1, p2, p3, p4, offset) =
    let (d12 = Subtract(p1,p2))
    let (d34 = Subtract(p3, p4))
    let (o12 = offset / Length(d12))
    let (o34 = offset / Length(d34))
    let (n12 = [d12[1] * o12, -d12[0] * o12])
    let (n34 = [d34[1] * o34, -d34[0] * o34]) 
    let (result = Solve(d12[0], -d34[0], (p3[0] + n34[0]) - (p2[0] + n12[0]),
                        d12[1], -d34[1], (p3[1] + n34[1]) - (p2[1] + n12[1])))
        [p2[0] + n12[0] + result[0] * d12[0],
         p2[1] + n12[1] + result[0] * d12[1]];
         
function Solve(m11, m12, r1, m21, m22, r2) =
    let (d = m12 * m21 - m22 * m11)
    (d == 0)
        ? [0.0, 0,0]
        : ((m11 == 0)
           ? let (b = r1 / m12) [(r2 - m22 * b) / m21, b]
           : let (b = (r1 * m21 - r2 * m11) / d) [(r1 - m12 * b) / m11, b]);
           
function Length2(p) = p[0]*p[0] + p[1]*p[1];
function Length(p) = sqrt(Length2(p));
function Subtract(v1, v2) = [v1[0] - v2[0], v1[1] - v2[1]];
function Dot(v1, v2) = v1[0] * v2[0] + v1[1] * v2[1];

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

SliceAngle = 1.5;



FixedCrossSection = [[0, 0],
                     [1, 0.4],
                     [1, 0.6],
                     [0, 1]];

function Slice(angle, i, pitch, innerRadius, threadDepth, threadHeight) =
    let (dx = cos(angle) * (innerRadius + threadDepth * FixedCrossSection[i][0]))
    let (dy = sin(angle) * (innerRadius + threadDepth * FixedCrossSection[i][0]))
    let (h = pitch * angle / 360)
        [dx, dy, FixedCrossSection[i][1] * threadHeight + h];
        
function Face(angle, i, j) =
    let (index = angle)
    let (s0 = index * len(FixedCrossSection))
    let (p0 = s0 + i)
    let (p1 = s0 + (i + 1) % len(FixedCrossSection))
    let (s1 = (index + 1) * len(FixedCrossSection))
    let (p2 = s1 + (i + 1) % len(FixedCrossSection))
    let (p3 = s1 + i)
    (j == 0) ? [p3,p1,p0] : [p2,p1,p3];
    
module Screw(radius, height, threadDepth, threadHeight) {
    difference() {
        union() {
            cylinder(r=radius+0.01,h=height, $fn=360/SliceAngle);
            
            pitch = threadHeight * 1.2; //(== threadHeight * (1 + 0.6 - 0.4));
            
            maxAngle = (360 * (height+threadHeight) / pitch);
            slices = ceil(maxAngle / SliceAngle);

            p = [for (angle = [0:slices])
                for (i = [0:len(FixedCrossSection)-1])
                    Slice(angle * SliceAngle, i, pitch, radius, threadDepth, threadHeight)];
                            
            f = [[for (i = [0:len(FixedCrossSection)-1]) i],
                 for (angle = [0:slices-1])
                    for (i = [0:len(FixedCrossSection)-1])
                        for (j = [0:1])
                            Face(angle, i, j),
                  [for (i = [len(FixedCrossSection)-1:-1:0]) i + slices*len(FixedCrossSection)]];
         
            translate([0,0,-threadHeight])          
                polyhedron(
                    points = p,
                    faces = f,
                    convexity = 10);
        }
        
        translate([-(radius+threadDepth)*2,-(radius+threadDepth)*2, 0])
            union () {
                translate([0,0,-2*threadHeight])
                    cube([(radius+threadDepth)*4,(radius+threadDepth)*4, 2*threadHeight]);
                translate([0,0,height])
                    cube([(radius+threadDepth)*4,(radius+threadDepth)*4, 2*threadHeight]);
            }
    }
}

