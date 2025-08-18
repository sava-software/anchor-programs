package software.sava.anchor.programs.loopscale.anchor.types;

import software.sava.core.borsh.Borsh;

public record CollateralCaps(PodU64CBPS maxAllocationPct, PodU64 currentAllocationAmount) implements Borsh {

  public static final int BYTES = 16;

  public static CollateralCaps read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var maxAllocationPct = PodU64CBPS.read(_data, i);
    i += Borsh.len(maxAllocationPct);
    final var currentAllocationAmount = PodU64.read(_data, i);
    return new CollateralCaps(maxAllocationPct, currentAllocationAmount);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.write(maxAllocationPct, _data, i);
    i += Borsh.write(currentAllocationAmount, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
