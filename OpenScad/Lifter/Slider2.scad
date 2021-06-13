Length = 32;
ChannelWidth = 27.5;
Depth = 24.925;
Chamfer = 1;
InnerEdge = -4;

TrackSpacing = 12;
TrackWidth = 2.5;
TrackOffset = 6.175; // Previously 5.675
HoleOffset = 14;


$fn = 72;

difference() {
    rotate([90,0,0])
    linear_extrude(height=Length, center=true)
        polygon([[ChannelWidth/2, TrackOffset],
                 [ChannelWidth/2, Chamfer],
                 [ChannelWidth/2 - Chamfer, 0],
                 [InnerEdge, 0],
                 [InnerEdge, Depth],
                 [ChannelWidth/2 - TrackWidth, Depth],
                 [ChannelWidth/2 - TrackWidth, TrackOffset]]);
    
    union () {
        translate([8,0,-1])
            cylinder(r=2,h=Depth+2);
        translate([0,8,-1])
            cylinder(r=2,h=Depth+2);
        translate([0,-8,-1])
            cylinder(r=2,h=Depth+2);
    }
    
}
