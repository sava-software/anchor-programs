package software.sava.anchor.programs.flash.perpetuals.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record CollectRebateLog(PublicKey owner,
                               PublicKey tokenStake,
                               long rebateAmount,
                               long rebateUsd,
                               long[] padding) implements Borsh {

  public static final int BYTES = 96;
  public static final int PADDING_LEN = 2;

  public static CollectRebateLog read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var owner = readPubKey(_data, i);
    i += 32;
    final var tokenStake = readPubKey(_data, i);
    i += 32;
    final var rebateAmount = getInt64LE(_data, i);
    i += 8;
    final var rebateUsd = getInt64LE(_data, i);
    i += 8;
    final var padding = new long[2];
    Borsh.readArray(padding, _data, i);
    return new CollectRebateLog(owner,
                                tokenStake,
                                rebateAmount,
                                rebateUsd,
                                padding);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    owner.write(_data, i);
    i += 32;
    tokenStake.write(_data, i);
    i += 32;
    putInt64LE(_data, i, rebateAmount);
    i += 8;
    putInt64LE(_data, i, rebateUsd);
    i += 8;
    i += Borsh.writeArrayChecked(padding, 2, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
