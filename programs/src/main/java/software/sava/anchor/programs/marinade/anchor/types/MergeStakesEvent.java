package software.sava.anchor.programs.marinade.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record MergeStakesEvent(PublicKey state,
                               long epoch,
                               int destinationStakeIndex,
                               PublicKey destinationStakeAccount,
                               long lastUpdateDestinationStakeDelegation,
                               int sourceStakeIndex,
                               PublicKey sourceStakeAccount,
                               long lastUpdateSourceStakeDelegation,
                               int validatorIndex,
                               PublicKey validatorVote,
                               long extraDelegated,
                               long returnedStakeRent,
                               long validatorActiveBalance,
                               long totalActiveBalance,
                               long operationalSolBalance) implements Borsh {

  public static final int BYTES = 204;

  public static MergeStakesEvent read(final byte[] _data, final int offset) {
    int i = offset;
    final var state = readPubKey(_data, i);
    i += 32;
    final var epoch = getInt64LE(_data, i);
    i += 8;
    final var destinationStakeIndex = getInt32LE(_data, i);
    i += 4;
    final var destinationStakeAccount = readPubKey(_data, i);
    i += 32;
    final var lastUpdateDestinationStakeDelegation = getInt64LE(_data, i);
    i += 8;
    final var sourceStakeIndex = getInt32LE(_data, i);
    i += 4;
    final var sourceStakeAccount = readPubKey(_data, i);
    i += 32;
    final var lastUpdateSourceStakeDelegation = getInt64LE(_data, i);
    i += 8;
    final var validatorIndex = getInt32LE(_data, i);
    i += 4;
    final var validatorVote = readPubKey(_data, i);
    i += 32;
    final var extraDelegated = getInt64LE(_data, i);
    i += 8;
    final var returnedStakeRent = getInt64LE(_data, i);
    i += 8;
    final var validatorActiveBalance = getInt64LE(_data, i);
    i += 8;
    final var totalActiveBalance = getInt64LE(_data, i);
    i += 8;
    final var operationalSolBalance = getInt64LE(_data, i);
    return new MergeStakesEvent(state,
                                epoch,
                                destinationStakeIndex,
                                destinationStakeAccount,
                                lastUpdateDestinationStakeDelegation,
                                sourceStakeIndex,
                                sourceStakeAccount,
                                lastUpdateSourceStakeDelegation,
                                validatorIndex,
                                validatorVote,
                                extraDelegated,
                                returnedStakeRent,
                                validatorActiveBalance,
                                totalActiveBalance,
                                operationalSolBalance);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    state.write(_data, i);
    i += 32;
    putInt64LE(_data, i, epoch);
    i += 8;
    putInt32LE(_data, i, destinationStakeIndex);
    i += 4;
    destinationStakeAccount.write(_data, i);
    i += 32;
    putInt64LE(_data, i, lastUpdateDestinationStakeDelegation);
    i += 8;
    putInt32LE(_data, i, sourceStakeIndex);
    i += 4;
    sourceStakeAccount.write(_data, i);
    i += 32;
    putInt64LE(_data, i, lastUpdateSourceStakeDelegation);
    i += 8;
    putInt32LE(_data, i, validatorIndex);
    i += 4;
    validatorVote.write(_data, i);
    i += 32;
    putInt64LE(_data, i, extraDelegated);
    i += 8;
    putInt64LE(_data, i, returnedStakeRent);
    i += 8;
    putInt64LE(_data, i, validatorActiveBalance);
    i += 8;
    putInt64LE(_data, i, totalActiveBalance);
    i += 8;
    putInt64LE(_data, i, operationalSolBalance);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return 32
         + 8
         + 4
         + 32
         + 8
         + 4
         + 32
         + 8
         + 4
         + 32
         + 8
         + 8
         + 8
         + 8
         + 8;
  }
}
