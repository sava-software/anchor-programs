package software.sava.anchor.programs.drift.anchor.types;

import software.sava.core.borsh.Borsh;

public enum FeatureBitFlags implements Borsh.Enum {

  MmOracleUpdate,
  MedianTriggerPrice,
  BuilderCodes,
  BuilderReferral;

  public static FeatureBitFlags read(final byte[] _data, final int offset) {
    return Borsh.read(FeatureBitFlags.values(), _data, offset);
  }
}