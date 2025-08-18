package software.sava.anchor.programs.loopscale.anchor.types;

import software.sava.core.borsh.Borsh;

public record LoanUnlockParams(byte[] assetIndexGuidance) implements Borsh {

  public static LoanUnlockParams read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final byte[] assetIndexGuidance = Borsh.readbyteVector(_data, offset);
    return new LoanUnlockParams(assetIndexGuidance);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.writeVector(assetIndexGuidance, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return Borsh.lenVector(assetIndexGuidance);
  }
}
