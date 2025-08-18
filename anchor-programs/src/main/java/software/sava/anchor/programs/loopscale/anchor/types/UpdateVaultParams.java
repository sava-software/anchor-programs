package software.sava.anchor.programs.loopscale.anchor.types;

import software.sava.core.borsh.Borsh;

public record UpdateVaultParams(boolean depositsEnabled, boolean initVaultRewardsInfo) implements Borsh {

  public static final int BYTES = 2;

  public static UpdateVaultParams read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var depositsEnabled = _data[i] == 1;
    ++i;
    final var initVaultRewardsInfo = _data[i] == 1;
    return new UpdateVaultParams(depositsEnabled, initVaultRewardsInfo);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    _data[i] = (byte) (depositsEnabled ? 1 : 0);
    ++i;
    _data[i] = (byte) (initVaultRewardsInfo ? 1 : 0);
    ++i;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
