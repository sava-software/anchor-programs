package software.sava.anchor.programs.kamino.lend.anchor.types;

import software.sava.core.borsh.Borsh;

public record BorrowRateCurve(CurvePoint[] points) implements Borsh {

  public static final int BYTES = 88;
  public static final int POINTS_LEN = 11;

  public static BorrowRateCurve read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var points = new CurvePoint[11];
    Borsh.readArray(points, CurvePoint::read, _data, offset);
    return new BorrowRateCurve(points);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.writeArray(points, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
