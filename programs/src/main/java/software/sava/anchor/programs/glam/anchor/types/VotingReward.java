package software.sava.anchor.programs.glam.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record VotingReward(PublicKey rewardMint,
                           PublicKey rewardVault,
                           long rewardPerProposal) implements Borsh {

  public static final int BYTES = 72;

  public static VotingReward read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var rewardMint = readPubKey(_data, i);
    i += 32;
    final var rewardVault = readPubKey(_data, i);
    i += 32;
    final var rewardPerProposal = getInt64LE(_data, i);
    return new VotingReward(rewardMint, rewardVault, rewardPerProposal);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    rewardMint.write(_data, i);
    i += 32;
    rewardVault.write(_data, i);
    i += 32;
    putInt64LE(_data, i, rewardPerProposal);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
