package software.sava.anchor.programs.jupiter.governance.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record GovernorSetVotingReward(PublicKey governor,
                                      PublicKey rewardMint,
                                      long rewardPerProposal) implements Borsh {

  public static final int BYTES = 72;

  public static GovernorSetVotingReward read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var governor = readPubKey(_data, i);
    i += 32;
    final var rewardMint = readPubKey(_data, i);
    i += 32;
    final var rewardPerProposal = getInt64LE(_data, i);
    return new GovernorSetVotingReward(governor, rewardMint, rewardPerProposal);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    governor.write(_data, i);
    i += 32;
    rewardMint.write(_data, i);
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
