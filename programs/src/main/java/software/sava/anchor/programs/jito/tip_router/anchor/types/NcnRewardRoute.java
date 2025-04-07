package software.sava.anchor.programs.jito.tip_router.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;

public record NcnRewardRoute(PublicKey operator, BaseRewardRouterRewards[] ncnFeeGroupRewards) implements Borsh {

  public static final int BYTES = 96;

  public static NcnRewardRoute read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var operator = readPubKey(_data, i);
    i += 32;
    final var ncnFeeGroupRewards = new BaseRewardRouterRewards[8];
    Borsh.readArray(ncnFeeGroupRewards, BaseRewardRouterRewards::read, _data, i);
    return new NcnRewardRoute(operator, ncnFeeGroupRewards);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    operator.write(_data, i);
    i += 32;
    i += Borsh.writeArray(ncnFeeGroupRewards, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
