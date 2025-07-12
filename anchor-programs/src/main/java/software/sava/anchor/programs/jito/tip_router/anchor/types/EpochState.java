package software.sava.anchor.programs.jito.tip_router.anchor.types;

import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.rpc.Filter;
import software.sava.rpc.json.http.response.AccountInfo;

import static software.sava.anchor.AnchorUtil.parseDiscriminator;
import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record EpochState(PublicKey _address,
                         Discriminator discriminator,
                         PublicKey ncn,
                         long epoch,
                         int bump,
                         long slotCreated,
                         boolean wasTieBreakerSet,
                         long slotConsensusReached,
                         long operatorCount,
                         long vaultCount,
                         EpochAccountStatus accountStatus,
                         Progress setWeightProgress,
                         Progress epochSnapshotProgress,
                         Progress[] operatorSnapshotProgress,
                         Progress votingProgress,
                         Progress validationProgress,
                         Progress uploadProgress,
                         Progress totalDistributionProgress,
                         Progress baseDistributionProgress,
                         Progress[] ncnDistributionProgress,
                         boolean isClosing,
                         byte[] reserved) implements Borsh {

  public static final int BYTES = 58879;
  public static final int OPERATOR_SNAPSHOT_PROGRESS_LEN = 256;
  public static final int NCN_DISTRIBUTION_PROGRESS_LEN = 2048;
  public static final int RESERVED_LEN = 1023;
  public static final Filter SIZE_FILTER = Filter.createDataSizeFilter(BYTES);

  public static final int NCN_OFFSET = 8;
  public static final int EPOCH_OFFSET = 40;
  public static final int BUMP_OFFSET = 48;
  public static final int SLOT_CREATED_OFFSET = 49;
  public static final int WAS_TIE_BREAKER_SET_OFFSET = 57;
  public static final int SLOT_CONSENSUS_REACHED_OFFSET = 58;
  public static final int OPERATOR_COUNT_OFFSET = 66;
  public static final int VAULT_COUNT_OFFSET = 74;
  public static final int ACCOUNT_STATUS_OFFSET = 82;
  public static final int SET_WEIGHT_PROGRESS_OFFSET = 2391;
  public static final int EPOCH_SNAPSHOT_PROGRESS_OFFSET = 2415;
  public static final int OPERATOR_SNAPSHOT_PROGRESS_OFFSET = 2439;
  public static final int VOTING_PROGRESS_OFFSET = 8583;
  public static final int VALIDATION_PROGRESS_OFFSET = 8607;
  public static final int UPLOAD_PROGRESS_OFFSET = 8631;
  public static final int TOTAL_DISTRIBUTION_PROGRESS_OFFSET = 8655;
  public static final int BASE_DISTRIBUTION_PROGRESS_OFFSET = 8679;
  public static final int NCN_DISTRIBUTION_PROGRESS_OFFSET = 8703;
  public static final int IS_CLOSING_OFFSET = 57855;
  public static final int RESERVED_OFFSET = 57856;

  public static Filter createNcnFilter(final PublicKey ncn) {
    return Filter.createMemCompFilter(NCN_OFFSET, ncn);
  }

  public static Filter createEpochFilter(final long epoch) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, epoch);
    return Filter.createMemCompFilter(EPOCH_OFFSET, _data);
  }

  public static Filter createBumpFilter(final int bump) {
    return Filter.createMemCompFilter(BUMP_OFFSET, new byte[]{(byte) bump});
  }

  public static Filter createSlotCreatedFilter(final long slotCreated) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, slotCreated);
    return Filter.createMemCompFilter(SLOT_CREATED_OFFSET, _data);
  }

  public static Filter createWasTieBreakerSetFilter(final boolean wasTieBreakerSet) {
    return Filter.createMemCompFilter(WAS_TIE_BREAKER_SET_OFFSET, new byte[]{(byte) (wasTieBreakerSet ? 1 : 0)});
  }

  public static Filter createSlotConsensusReachedFilter(final long slotConsensusReached) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, slotConsensusReached);
    return Filter.createMemCompFilter(SLOT_CONSENSUS_REACHED_OFFSET, _data);
  }

  public static Filter createOperatorCountFilter(final long operatorCount) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, operatorCount);
    return Filter.createMemCompFilter(OPERATOR_COUNT_OFFSET, _data);
  }

  public static Filter createVaultCountFilter(final long vaultCount) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, vaultCount);
    return Filter.createMemCompFilter(VAULT_COUNT_OFFSET, _data);
  }

  public static Filter createSetWeightProgressFilter(final Progress setWeightProgress) {
    return Filter.createMemCompFilter(SET_WEIGHT_PROGRESS_OFFSET, setWeightProgress.write());
  }

  public static Filter createEpochSnapshotProgressFilter(final Progress epochSnapshotProgress) {
    return Filter.createMemCompFilter(EPOCH_SNAPSHOT_PROGRESS_OFFSET, epochSnapshotProgress.write());
  }

  public static Filter createVotingProgressFilter(final Progress votingProgress) {
    return Filter.createMemCompFilter(VOTING_PROGRESS_OFFSET, votingProgress.write());
  }

  public static Filter createValidationProgressFilter(final Progress validationProgress) {
    return Filter.createMemCompFilter(VALIDATION_PROGRESS_OFFSET, validationProgress.write());
  }

  public static Filter createUploadProgressFilter(final Progress uploadProgress) {
    return Filter.createMemCompFilter(UPLOAD_PROGRESS_OFFSET, uploadProgress.write());
  }

  public static Filter createTotalDistributionProgressFilter(final Progress totalDistributionProgress) {
    return Filter.createMemCompFilter(TOTAL_DISTRIBUTION_PROGRESS_OFFSET, totalDistributionProgress.write());
  }

  public static Filter createBaseDistributionProgressFilter(final Progress baseDistributionProgress) {
    return Filter.createMemCompFilter(BASE_DISTRIBUTION_PROGRESS_OFFSET, baseDistributionProgress.write());
  }

  public static Filter createIsClosingFilter(final boolean isClosing) {
    return Filter.createMemCompFilter(IS_CLOSING_OFFSET, new byte[]{(byte) (isClosing ? 1 : 0)});
  }

  public static EpochState read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static EpochState read(final AccountInfo<byte[]> accountInfo) {
    return read(accountInfo.pubKey(), accountInfo.data(), 0);
  }

  public static EpochState read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], EpochState> FACTORY = EpochState::read;

  public static EpochState read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = parseDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var ncn = readPubKey(_data, i);
    i += 32;
    final var epoch = getInt64LE(_data, i);
    i += 8;
    final var bump = _data[i] & 0xFF;
    ++i;
    final var slotCreated = getInt64LE(_data, i);
    i += 8;
    final var wasTieBreakerSet = _data[i] == 1;
    ++i;
    final var slotConsensusReached = getInt64LE(_data, i);
    i += 8;
    final var operatorCount = getInt64LE(_data, i);
    i += 8;
    final var vaultCount = getInt64LE(_data, i);
    i += 8;
    final var accountStatus = EpochAccountStatus.read(_data, i);
    i += Borsh.len(accountStatus);
    final var setWeightProgress = Progress.read(_data, i);
    i += Borsh.len(setWeightProgress);
    final var epochSnapshotProgress = Progress.read(_data, i);
    i += Borsh.len(epochSnapshotProgress);
    final var operatorSnapshotProgress = new Progress[256];
    i += Borsh.readArray(operatorSnapshotProgress, Progress::read, _data, i);
    final var votingProgress = Progress.read(_data, i);
    i += Borsh.len(votingProgress);
    final var validationProgress = Progress.read(_data, i);
    i += Borsh.len(validationProgress);
    final var uploadProgress = Progress.read(_data, i);
    i += Borsh.len(uploadProgress);
    final var totalDistributionProgress = Progress.read(_data, i);
    i += Borsh.len(totalDistributionProgress);
    final var baseDistributionProgress = Progress.read(_data, i);
    i += Borsh.len(baseDistributionProgress);
    final var ncnDistributionProgress = new Progress[2048];
    i += Borsh.readArray(ncnDistributionProgress, Progress::read, _data, i);
    final var isClosing = _data[i] == 1;
    ++i;
    final var reserved = new byte[1023];
    Borsh.readArray(reserved, _data, i);
    return new EpochState(_address,
                          discriminator,
                          ncn,
                          epoch,
                          bump,
                          slotCreated,
                          wasTieBreakerSet,
                          slotConsensusReached,
                          operatorCount,
                          vaultCount,
                          accountStatus,
                          setWeightProgress,
                          epochSnapshotProgress,
                          operatorSnapshotProgress,
                          votingProgress,
                          validationProgress,
                          uploadProgress,
                          totalDistributionProgress,
                          baseDistributionProgress,
                          ncnDistributionProgress,
                          isClosing,
                          reserved);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    ncn.write(_data, i);
    i += 32;
    putInt64LE(_data, i, epoch);
    i += 8;
    _data[i] = (byte) bump;
    ++i;
    putInt64LE(_data, i, slotCreated);
    i += 8;
    _data[i] = (byte) (wasTieBreakerSet ? 1 : 0);
    ++i;
    putInt64LE(_data, i, slotConsensusReached);
    i += 8;
    putInt64LE(_data, i, operatorCount);
    i += 8;
    putInt64LE(_data, i, vaultCount);
    i += 8;
    i += Borsh.write(accountStatus, _data, i);
    i += Borsh.write(setWeightProgress, _data, i);
    i += Borsh.write(epochSnapshotProgress, _data, i);
    i += Borsh.writeArray(operatorSnapshotProgress, _data, i);
    i += Borsh.write(votingProgress, _data, i);
    i += Borsh.write(validationProgress, _data, i);
    i += Borsh.write(uploadProgress, _data, i);
    i += Borsh.write(totalDistributionProgress, _data, i);
    i += Borsh.write(baseDistributionProgress, _data, i);
    i += Borsh.writeArray(ncnDistributionProgress, _data, i);
    _data[i] = (byte) (isClosing ? 1 : 0);
    ++i;
    i += Borsh.writeArray(reserved, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
