package software.sava.anchor.programs.jito.tip_router.anchor.types;

import java.math.BigInteger;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt128LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt128LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record StMintEntry(PublicKey stMint,
                          NcnFeeGroup ncnFeeGroup,
                          long rewardMultiplierBps,
                          PublicKey switchboardFeed,
                          BigInteger noFeedWeight,
                          byte[] reserved) implements Borsh {

  public static final int BYTES = 217;

  public static StMintEntry read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var stMint = readPubKey(_data, i);
    i += 32;
    final var ncnFeeGroup = NcnFeeGroup.read(_data, i);
    i += Borsh.len(ncnFeeGroup);
    final var rewardMultiplierBps = getInt64LE(_data, i);
    i += 8;
    final var switchboardFeed = readPubKey(_data, i);
    i += 32;
    final var noFeedWeight = getInt128LE(_data, i);
    i += 16;
    final var reserved = new byte[128];
    Borsh.readArray(reserved, _data, i);
    return new StMintEntry(stMint,
                           ncnFeeGroup,
                           rewardMultiplierBps,
                           switchboardFeed,
                           noFeedWeight,
                           reserved);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    stMint.write(_data, i);
    i += 32;
    i += Borsh.write(ncnFeeGroup, _data, i);
    putInt64LE(_data, i, rewardMultiplierBps);
    i += 8;
    switchboardFeed.write(_data, i);
    i += 32;
    putInt128LE(_data, i, noFeedWeight);
    i += 16;
    i += Borsh.writeArray(reserved, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
