package software.sava.anchor.programs.glam_v0.anchor.types;

import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.rpc.Filter;

import static software.sava.anchor.AnchorUtil.parseDiscriminator;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;
import static software.sava.core.programs.Discriminator.toDiscriminator;

public record PolicyAccount(PublicKey _address, Discriminator discriminator, long lockedUntilTs) implements Borsh {

  public static final int BYTES = 16;
  public static final Filter SIZE_FILTER = Filter.createDataSizeFilter(BYTES);

  public static final Discriminator DISCRIMINATOR = toDiscriminator(218, 201, 183, 164, 156, 127, 81, 175);
  public static final Filter DISCRIMINATOR_FILTER = Filter.createMemCompFilter(0, DISCRIMINATOR.data());

  public static final int LOCKED_UNTIL_TS_OFFSET = 8;

  public static Filter createLockedUntilTsFilter(final long lockedUntilTs) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, lockedUntilTs);
    return Filter.createMemCompFilter(LOCKED_UNTIL_TS_OFFSET, _data);
  }

  public static PolicyAccount read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static PolicyAccount read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], PolicyAccount> FACTORY = PolicyAccount::read;

  public static PolicyAccount read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = parseDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var lockedUntilTs = getInt64LE(_data, i);
    return new PolicyAccount(_address, discriminator, lockedUntilTs);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    putInt64LE(_data, i, lockedUntilTs);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
