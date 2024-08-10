package software.sava.anchor.programs.marinade.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;

// FeeCents, same as Fee but / 1_000_000 instead of 10_000
// 1 FeeCent = 0.0001%, 10_000 FeeCent = 1%, 1_000_000 FeeCent = 100%
public record FeeCents(int bpCents) implements Borsh {

  public static final int BYTES = 4;

  public static FeeCents read(final byte[] _data, final int offset) {
    int i = offset;
    final var bpCents = getInt32LE(_data, i);
    return new FeeCents(bpCents);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt32LE(_data, i, bpCents);
    i += 4;
    return i - offset;
  }

  @Override
  public int l() {
    return 4;
  }
}