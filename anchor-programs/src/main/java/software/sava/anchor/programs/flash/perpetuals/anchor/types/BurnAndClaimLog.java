package software.sava.anchor.programs.flash.perpetuals.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record BurnAndClaimLog(PublicKey owner,
                              PublicKey nftMint,
                              long claimAmount,
                              long currentTimestamp) implements Borsh {

  public static final int BYTES = 80;

  public static BurnAndClaimLog read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var owner = readPubKey(_data, i);
    i += 32;
    final var nftMint = readPubKey(_data, i);
    i += 32;
    final var claimAmount = getInt64LE(_data, i);
    i += 8;
    final var currentTimestamp = getInt64LE(_data, i);
    return new BurnAndClaimLog(owner,
                               nftMint,
                               claimAmount,
                               currentTimestamp);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    owner.write(_data, i);
    i += 32;
    nftMint.write(_data, i);
    i += 32;
    putInt64LE(_data, i, claimAmount);
    i += 8;
    putInt64LE(_data, i, currentTimestamp);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
