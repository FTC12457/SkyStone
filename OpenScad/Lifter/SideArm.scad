$fn = 72;
TipThickness = 9;
ArmThickness = 4;
TipChamfer = 5;

Length = 106;
padding = 2;
Chamfer = 0.5;
ServoThickness = 16;
ServoChamfer = 2;

p0 = [-128,16]; r0i = 10;   r0 = r0i + padding;  
p1 = [-16, 0];  r1i = 4;    r1 = r1i + 4;
p2 = [0, 8];    r2i = 2;    r2 = r2i + padding;
p3 = [16, 0];   r3i = r1i;  r3 = r3i + padding;
p4 = [0, -8];   r4i = r2i;  r4 = r4i + padding;
r5 = 4; p5 = [p1.x, p1.y + r1 + r5];
r6 = 4; p6 = [p1.x, p1.y - r1 - r6];

p05 = RLTangent(p0, p5, r0, r5, 1);
p50 = RLTangent(p5, p0, r5, r0, 1);

p52 = RLTangent(p5, p2, r5, r2, -1);
p25 = RLTangent(p2, p5, r2, r5, -1);

p23 = RRTangent(p2, p3, r2, r3, 1);
p32 = RRTangent(p3, p2, r3, r2, -1);

p34 = RRTangent(p3, p4, r3, r4, 1);
p43 = RRTangent(p4, p3, r4, r3, -1);

p46 = RLTangent(p4, p6, r6, r4, 1);
p64 = RLTangent(p6, p4, r4, r6, 1);

p10 = RRTangent(p1, p0, r1, r0, 1);
p01 = RRTangent(p0, p1, r0, r1, -1);

//rotate([0,0,50])
difference() {
    union() {
        linear_extrude(height = ArmThickness)
            polygon([p05, p50, p52, p64, p6, p1, p10, p01]);
        linear_extrude(height = TipThickness)
            polygon([p50, p52, p25, p23, p32, p34, p43, p46, p64, p6, p1, p10]);
      
        
        translate([p0.x, p0.y, 0])
            cylinder(r=r0, h = ArmThickness);
        
        translate([p1.x, p1.y, 0])
            cylinder(r=r1, h = TipThickness);
        intersection () {
            union () {
                translate([p1.x, p1.y, ArmThickness])
                    cylinder(r1=r1 + TipChamfer, r2 = r1, h = TipThickness - ArmThickness);
                
                translate([p2.x, p2.y, TipThickness-1])
                    cylinder(r1=r2 + ServoChamfer + 1, r2 = r2, h = ServoChamfer + 1);
                translate([p4.x, p4.y, TipThickness-1])
                    cylinder(r1=r4 + ServoChamfer + 1, r2 = r4, h = ServoChamfer + 1);
                
                translate([0,0,TipThickness])
                rotate([90,0,0])
                linear_extrude(height = (p2.y - p4.y), center = true)
                    polygon([[r2 + ServoChamfer + 1, -1],
                             [r2, ServoChamfer], [r2, ServoThickness],
                             [-r2, ServoThickness], [-r2, ServoChamfer],
                             [-(r2 + ServoChamfer + 1), -1]]);
            }
            
            translate([0,0,-1])
                linear_extrude(height = TipThickness + ServoThickness + 2)
                    polygon([p05, p50, p52, p25, p23, p32, p34, p43, p46,
                             p64, p6, p1, p10, p01]);
        }
        
        translate([p2.x, p2.y, 0])
            cylinder(r=r2, h = TipThickness + ServoThickness);
        translate([p3.x, p3.y, 0])
            cylinder(r=r3, h = TipThickness);
        translate([p4.x, p4.y, 0])
            cylinder(r=r4, h = TipThickness + ServoThickness);
        
       // translate([-r2, -(p2.y - p4.y)/2, TipThickness-1])
       // cube([r2*2, p2.y - p4.y, 1 + ServoThickness]);
    }
    
    union () {
        translate([p5.x, p5.y, 0])
            cylinder(r = r5, h = 100, center = true);
        translate([p6.x, p6.y, 0])
            cylinder(r = r6, h = 100, center = true);
        
        TappedHole(p0, 3);
        translate([p0.x, p0.y, 0])
            rotate([0,0,45+atan2(p0.y,p0.x)]) {
                TappedHole([ 8, 0], 2);
                TappedHole([-8, 0], 2);
                TappedHole([0, 8], 2);
                TappedHole([0, -8], 2);
            }
        
        TappedHole(p1, r1i);
        TappedHole(p2, r2i);
        TappedHole(p3, r3i);
        TappedHole(p4, r4i);
    }
}

module Mark(pp) {
    echo(pp);
    for (i = [0:len(pp)-1]) {
        echo(pp[i]);
        translate([pp[i].x, pp[i].y, 0])
            cylinder(r=1,h=20 + i * 5);
    }
}

module TappedHole(p, r) {
        translate([p.x, p.y, -1])
            cylinder(r=r, h = TipThickness + ServoThickness + 2);
        translate([p.x, p.y, -Chamfer])
            cylinder(r1=r + 2*Chamfer, r2 = r - Chamfer, h = Chamfer * 3);
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

