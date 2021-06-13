Thickness = 6.175;

R8 = 2.4;

R0 = 12;
R1 = 20;
R2 = R8+2;
R3 = R8+2;
R4 = R8+2;

R6 = 2.25;

Length = 136;

p0 = [0,0];
p1 = [Length-20, -5];

p3 = [Length-4,16];

p2 = [p3.x + 8 * cos(60) * sqrt(2), p3.y + 8 * sin(60) * sqrt(2)];

p4 = p1;//[Length - 12, -9];

p01 = RRTangent(p0, p1, R0, R1, -1);
p10 = RRTangent(p1, p0, R1, R0, 1);
p12 = RRTangent(p1, p2, R1, R2, -1);
p21 = RRTangent(p2, p1, R2, R1, 1);

p23 = RRTangent(p2, p3, R2, R3, -1);
p32 = RRTangent(p3, p2, R3, R2, 1);

p34 = RLTangent(p3, p4, R3, R4, -1);
p43 = RLTangent(p4, p3, R4, R3, -1);

p40 = RLTangent(p4, p0, R4, R0, 1);
p04 = RLTangent(p0, p4, R0, R4, 1);


$fn = 72;

//translate([0,60,0])
//cube([100,100,10]);
//color("red") translate([-12,-12,-1])
//cube([145,145,.5]);
rotate([0,0,-45])
difference() {
    union() {
     //   linear_extrude(height=Thickness+2)
          //  polygon([p0, p1, p2, p3, p4]);
       
        linear_extrude(height=Thickness)
            polygon([p01, p10, p12, p21, p23, p32, p34, p43, p40, p04]);
        
        translate([p0[0], p0[1], 0])
            cylinder(r = R0, h = Thickness);

        intersection() {
            translate([0,0,-1])
            linear_extrude(height=Thickness+2)
                polygon([p1,p10, [p10.x, p10.y-20], [100,-100],
                        [p12.x+20,p12.y], p12]);
            translate([p1[0], p1[1], 0])
                cylinder(r = R1, h = Thickness);
        }

        translate([p2[0], p2[1], 0])
            cylinder(r = R2, h = Thickness);

        translate([p3[0], p3[1], 0])
            cylinder(r = R3, h = Thickness);

    }
    
    union () {
        translate([p4[0], p4[1], -1])
            cylinder(r = R4, h = Thickness+2);
        
        translate([p0[0], p0[1], -1])
            cylinder(r = 3, h = Thickness+2);
        
        rotate([0,0,34.5]) {
        translate([p0[0] + 0, p0[1] + 8, -1])
            cylinder(r = R6, h = Thickness+2);
        translate([p0[0] + 8, p0[1] + 0, -1])
            cylinder(r = R6, h = Thickness+2);
        translate([p0[0] + 0, p0[1] - 8, -1])
            cylinder(r = R6, h = Thickness+2);
        translate([p0[0] - 8, p0[1] + 0, -1])
            cylinder(r = R6, h = Thickness+2);
        }

        translate([p2[0], p2[1], -1])
            cylinder(r = R8, h = Thickness+2);

        translate([p3[0], p3[1], -1])
            cylinder(r = R8, h = Thickness+2);
     }
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

