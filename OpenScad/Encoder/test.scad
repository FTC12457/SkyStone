

import("Odometry_Holder.stl");

color("green")
translate([0,0,-11])
cube([20,20,26]);
color("red")
translate([5,0,-14])
cube([20,20,3.175]);

translate([50,0,0])
    import("omniWheelInsert.stl");
    
translate([-50,0,0])
    import("spacerBigOdometry.stl");
    
translate([-75,0,0])
    import("spacerSmallOdometry.stl");