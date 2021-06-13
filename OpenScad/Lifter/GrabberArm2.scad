Thickness = 6;

R8 = 2.4;

R0 = 12;
R1 = R8+2;
R2 = R8+2;
R3 = R8+2;
R4 = R8+2;

R6 = 2.25;

Length = 106;

p0 = [0,0];
p1 = [Length-5, -21];
p2 = [Length+8,0];
p3 = [Length,-8];
p4 = [Length - 12, -9];

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

dxy = [p04.x - p40.x, p04.y - p40.y];
l = sqrt(dxy.x*dxy.x + dxy.y * dxy.y);

p = [dxy.x / l, dxy.y / l];
n = [-p.y, p.x];

hookOffset = 12;


difference() {
    union() {
     //   linear_extrude(height=Thickness+2)
          //  polygon([p0, p1, p2, p3, p4]);
       
        linear_extrude(height=Thickness)
            polygon([p01, p10, p12, p21, p23, p32, p34, p43, p40, p04]);
        
        translate([p0[0], p0[1], 0])
            cylinder(r = R0, h = Thickness);

        translate([p1[0], p1[1], 0])
            cylinder(r = R1, h = Thickness);

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
        
        translate([p40.x + p.x * hookOffset + n.x * 4,
                  p40.y + p.y * hookOffset + n.y * 4, -1])
            cylinder(r = 1, h = 20);
        translate([p40.x + p.x * (hookOffset + 8) + n.x * 4,
                   p40.y + p.y * (hookOffset + 8) + n.y * 4, -1])
            cylinder(r = 1, h = 20);
        translate([p40.x + p.x * (hookOffset + 16) + n.x * 4,
                   p40.y + p.y * (hookOffset + 16) + n.y * 4, -1])
            cylinder(r = 1, h = 20);
        translate([p40.x + p.x * (hookOffset + 24) + n.x * 4,
                   p40.y + p.y * (hookOffset + 24) + n.y * 4, -1])
            cylinder(r = 1, h = 20);
        translate([p40.x + p.x * (hookOffset + 32) + n.x * 4,
                   p40.y + p.y * (hookOffset + 32) + n.y * 4, -1])
            cylinder(r = 1, h = 20);
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

