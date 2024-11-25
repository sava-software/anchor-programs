package software.sava.anchor.programs.moonshot.anchor.types;

import software.sava.core.borsh.Borsh;

public enum CurveType implements Borsh.Enum {

  LinearV1,
  ConstantProductV1;

  public static CurveType read(final byte[] _data, final int offset) {
    return Borsh.read(CurveType.values(), _data, offset);
  }
}