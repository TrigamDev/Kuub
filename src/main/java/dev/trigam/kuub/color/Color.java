package dev.trigam.kuub.color;

public class Color {

    public int red;
    public int green;
    public int blue;
    public int alpha;

    public Color ( int red, int green, int blue, int alpha ) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.alpha = alpha;
    }
    public Color ( int red, int green, int blue ) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.alpha = 255;
    }
    public String toString() { return String.format( "(Red: %s, Green: %s, Blue: %s, Alpha: %s)", this.red, this.green, this.blue, this.alpha ); }

    //
    //  RGB
    //

    public static Color fromRGB( int red, int green, int blue) { return new Color(red, green, blue ); }
    public static Color fromRGBInt( int rgb ) {
        return new Color(
            getRedFromRGBInt( rgb ),
            getGreenFromRGBInt( rgb ),
            getBlueFromRGBInt( rgb )
        );
    }

    public int toRGBInt() { return toRGBInt( this.red, this.green, this.blue ); }
    public static int toRGBInt( int red, int green, int blue ) { return redToRGBInt( red ) | greenToRGBInt( green ) | blueToRGBInt( blue ); }

    //
    //  RGB CONVERSION
    //

    public static int getRedFromRGBInt( int rgb ) { return ( rgb >> 16 ) & 0xFF; }
    public static int getGreenFromRGBInt( int rgb ) { return ( rgb >> 8 ) & 0xFF; }
    public static int getBlueFromRGBInt( int rgb ) { return rgb & 0xFF; }

    public static int redToRGBInt( int red ) { return red << 16; }
    public static int greenToRGBInt( int green ) { return green << 8; }
    public static int blueToRGBInt( int blue ) { return blue; }

    //
    //  RGBA
    //

    public static Color fromRGBA( int red, int green, int blue, int alpha ) { return new Color( red, green, blue, alpha ); }
    public static Color fromRGBAInt( int rgba ) {
        return new Color(
            getRedFromRGBAInt( rgba ),
            getGreenFromRGBAInt( rgba ),
            getBlueFromRGBAInt( rgba ),
            getAlphaFromRGBAInt( rgba )
        );
    }

    public int toRGBAInt() { return toRGBAInt( this.red, this.green, this.blue, this.alpha ); }
    public static int toRGBAInt( int red, int green, int blue, int alpha ) { return redToRGBAInt( red ) | greenToRGBAInt( green ) | blueToRGBAInt( blue ) | alphaToRGBAInt( alpha ); }

    //
    //  RGBA CONVERSION
    //

    public static int getRedFromRGBAInt( int rgb ) { return ( rgb >> 24 ) & 0xFF; }
    public static int getGreenFromRGBAInt( int rgb ) { return ( rgb >> 16 ) & 0xFF; }
    public static int getBlueFromRGBAInt( int rgb ) { return ( rgb >> 8 ) & 0xFF; }
    public static int getAlphaFromRGBAInt( int rgb ) { return rgb & 0xFF; }

    public static int redToRGBAInt( int red ) { return red << 24; }
    public static int greenToRGBAInt( int green ) { return green << 16; }
    public static int blueToRGBAInt( int blue ) { return blue << 8; }
    public static int alphaToRGBAInt( int alpha ) { return alpha; }

    //
    //  ARGB
    //

    public static Color fromARGB( int alpha, int red, int green, int blue) { return new Color(red, green, blue, alpha ); }
    public static Color fromARGBInt( int argb ) {
        return new Color(
            getRedFromARGBInt( argb ),
            getGreenFromARGBInt( argb ),
            getBlueFromARGBInt( argb ),
            getAlphaFromARGBInt( argb )
        );
    }

    public int toARGBInt() { return toARGBInt( this.alpha, this.red, this.green, this.blue ); }
    public static int toARGBInt( int alpha, int red, int green, int blue ) { return alphaToARGBInt( alpha ) | redToARGBInt( red ) | greenToARGBInt( green ) | blueToARGBInt( blue ); }

    //
    //  ARGB CONVERSION
    //

    public static int getAlphaFromARGBInt( int argb ) { return ( argb >>> 24 ); }
    public static int getRedFromARGBInt( int argb ) { return ( argb >> 16 ) & 0xFF; }
    public static int getGreenFromARGBInt( int argb ) { return ( argb >> 8 ) & 0xFF; }
    public static int getBlueFromARGBInt( int argb ) { return argb & 0xFF; }

    public static int alphaToARGBInt( int alpha ) { return alpha << 24; }
    public static int redToARGBInt( int red ) { return red << 16; }
    public static int greenToARGBInt( int green ) { return green << 8; }
    public static int blueToARGBInt( int blue ) { return blue; }

    //
    //  ABGR
    //

    public static Color fromABGR( int alpha, int blue, int green, int red) { return new Color(red, green, blue, alpha ); }
    public static Color fromABGRInt( int abgr ) {
        return new Color(
            getRedFromABGRInt( abgr ),
            getGreenFromABGRInt( abgr ),
            getBlueFromABGRInt( abgr ),
            getAlphaFromABGRInt( abgr )
        );
    }

    public int toABGRInt() { return toABGRInt( this.alpha, this.blue, this.green, this.red ); }
    public static int toABGRInt( int alpha, int blue, int green, int red ) { return alphaToABGRInt( alpha ) | blueToABGRInt( blue ) | greenToABGRInt( green ) | redToABGRInt( red ); }

    //
    //  ABGR CONVERSION
    //

    public static int getAlphaFromABGRInt( int argb ) { return argb >>> 24; }
    public static int getBlueFromABGRInt( int argb ) { return ( argb >> 16 ) & 0xFF; }
    public static int getGreenFromABGRInt( int argb ) { return ( argb >> 8 ) & 0xFF; }
    public static int getRedFromABGRInt( int argb ) { return argb & 0xFF; }

    public static int alphaToABGRInt( int alpha ) { return alpha << 24; }
    public static int blueToABGRInt( int blue ) { return blue << 16; }
    public static int greenToABGRInt( int green ) { return green << 8; }
    public static int redToABGRInt( int red ) { return red; }

    //
    //  HEX
    //

    public static Color fromHex( String hex ) {
        hex = hex.replace( "#", "" );
        return switch ( hex.length() ) {
            // RGB hex
            case 6 -> new Color(
                Integer.valueOf(hex.substring(0, 2), 16),
                Integer.valueOf(hex.substring(2, 4), 16),
                Integer.valueOf(hex.substring(4, 6), 16)
            );
            // RGBA hex
            case 8 -> new Color(
                Integer.valueOf(hex.substring(2, 4), 16),
                Integer.valueOf(hex.substring(4, 6), 16),
                Integer.valueOf(hex.substring(6, 8), 16),
                Integer.valueOf(hex.substring(0, 2), 16)
            );
            default -> null;
        };
    }

    public String toHex( boolean includeHashtag, boolean upperCase, boolean includeAlpha ) {
        return includeAlpha ?
            toHex( this.red, this.green, this.blue, this.alpha, includeHashtag, upperCase ) :
            toHex( this.red, this.green, this.blue, includeHashtag, upperCase );
    }
    public static String toHex( int red, int green, int blue, boolean includeHashtag, boolean upperCase ) {
        String hex = String.format( "%02x%02x%02x", red, green, blue );
        if ( includeHashtag ) hex = "#" + hex;
        return upperCase ? hex.toUpperCase() : hex;
    }
    public static String toHex( int red, int green, int blue, int alpha, boolean includeHashtag, boolean upperCase ) {
        String hex = String.format( "%02x%02x%02x%02x", alpha, red, green, blue );
        if ( includeHashtag ) hex = "#" + hex;
        return upperCase ? hex.toUpperCase() : hex;
    }

    public int toHexInt() { return toHexInt( toHex( this.red, this.green, this.blue, this.alpha, false, false ) ); }
    public static int toHexInt( String hex ) { return (int) Long.parseLong( hex.replace( "#", "" ), 16 ); }



    //
    //  TRANSFORMATIONS
    //

    public Color toOpaque() { return Color.fromRGB( this.red, this.green, this.blue ); }

    //
    //  UTIL
    //

    public static int channelFromFloat( float value ) { return (int) ( value * 255.0f ); }
    public static float floatFromChannel ( int value ) { return (float) value / 255.0f; }
}