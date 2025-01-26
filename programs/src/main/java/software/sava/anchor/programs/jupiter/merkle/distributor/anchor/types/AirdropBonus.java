package software.sava.anchor.programs.jupiter.merkle.distributor.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record AirdropBonus(// total bonus
                           long totalBonus,
                           long vestingDuration,
                           // total bonus
                           long totalClaimedBonus) implements Borsh {

  public static final int BYTES = 24;

  public static AirdropBonus read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var totalBonus = getInt64LE(_data, i);
    i += 8;
    final var vestingDuration = getInt64LE(_data, i);
    i += 8;
    final var totalClaimedBonus = getInt64LE(_data, i);
    return new AirdropBonus(totalBonus, vestingDuration, totalClaimedBonus);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt64LE(_data, i, totalBonus);
    i += 8;
    putInt64LE(_data, i, vestingDuration);
    i += 8;
    putInt64LE(_data, i, totalClaimedBonus);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
