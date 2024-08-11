package software.sava.anchor.programs.marinade.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record InitializeData(PublicKey adminAuthority,
                             PublicKey validatorManagerAuthority,
                             long minStake,
                             Fee rewardsFee,
                             LiqPoolInitializeData liqPool,
                             int additionalStakeRecordSpace,
                             int additionalValidatorRecordSpace,
                             long slotsForStakeDelta,
                             PublicKey pauseAuthority) implements Borsh {


  public static InitializeData read(final byte[] _data, final int offset) {
    int i = offset;
    final var adminAuthority = readPubKey(_data, i);
    i += 32;
    final var validatorManagerAuthority = readPubKey(_data, i);
    i += 32;
    final var minStake = getInt64LE(_data, i);
    i += 8;
    final var rewardsFee = Fee.read(_data, i);
    i += Borsh.len(rewardsFee);
    final var liqPool = LiqPoolInitializeData.read(_data, i);
    i += Borsh.len(liqPool);
    final var additionalStakeRecordSpace = getInt32LE(_data, i);
    i += 4;
    final var additionalValidatorRecordSpace = getInt32LE(_data, i);
    i += 4;
    final var slotsForStakeDelta = getInt64LE(_data, i);
    i += 8;
    final var pauseAuthority = readPubKey(_data, i);
    return new InitializeData(adminAuthority,
                              validatorManagerAuthority,
                              minStake,
                              rewardsFee,
                              liqPool,
                              additionalStakeRecordSpace,
                              additionalValidatorRecordSpace,
                              slotsForStakeDelta,
                              pauseAuthority);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    adminAuthority.write(_data, i);
    i += 32;
    validatorManagerAuthority.write(_data, i);
    i += 32;
    putInt64LE(_data, i, minStake);
    i += 8;
    i += Borsh.write(rewardsFee, _data, i);
    i += Borsh.write(liqPool, _data, i);
    putInt32LE(_data, i, additionalStakeRecordSpace);
    i += 4;
    putInt32LE(_data, i, additionalValidatorRecordSpace);
    i += 4;
    putInt64LE(_data, i, slotsForStakeDelta);
    i += 8;
    pauseAuthority.write(_data, i);
    i += 32;
    return i - offset;
  }

  @Override
  public int l() {
    return 32
         + 32
         + 8
         + Borsh.len(rewardsFee)
         + Borsh.len(liqPool)
         + 4
         + 4
         + 8
         + 32;
  }
}
