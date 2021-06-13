$fn = 72;


l = 70;
w = 120;
o = 32;
os2 = o * sqrt(2);
d = 32;
t = 1.51;
t2s = t * sqrt(2);


p0 = [0,0];
p1 = [l, 0];
p2 = [p1.x + o, o];
p3 = [p2.x, p2.y + t];
p4 = [p3.x - t, p3.y];
p5 = [p1.x - t, p1.y + t];
p6 = [p0.x, p0.y + t];
p7 = [p0.x, p4.y];

difference() {
    rotate([90,0,0])
        union() {
            linear_extrude(height = w)
                polygon([p0,p1,p2,p3,p4,p5,p6]);
            linear_extrude(height = t)
                polygon([p0, p1, p2, p4, p4, p7]);
            translate([0,0,w-t])
            linear_extrude(height = t)
                polygon([p0, p1, p2, p4, p4, p7]);
            
            intersection() {
                linear_extrude(height = w)
                    polygon([p0, p1, p2, p4, p4, p7]);
                union() {
                    translate([0,t,w-t])
                        rotate([0,90,0])
                            linear_extrude(height = l + w)
                                polygon([[0,0], [1,0], [0,1]]);
                    translate([0,t,t])
                        rotate([0,90,0])
                            linear_extrude(height = l + w)
                                polygon([[0,0], [-1,0], [0,1]]);
                }
            }
        }

    translate([16, -72,0])
        cylinder(r=2, h = 10, center = true);
    translate([16, -56, 0])
                cylinder(r=2, h = 10, center = true);
    translate([64, -72, 0])
                cylinder(r=2, h = 10, center = true);
    translate([64, -56, 0])
                cylinder(r=2, h = 10, center = true);
}